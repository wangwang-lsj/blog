package com.wanwan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanwan.enums.ResultCodeEnum;
import com.wanwan.model.dto.*;
import com.wanwan.exception.ServiceException;
import com.wanwan.mapper.RoleMapper;
import com.wanwan.mapper.RoleMenuMapper;
import com.wanwan.mapper.UserMapper;
import com.wanwan.model.dto.UserLoginDTO;
import com.wanwan.model.dto.UserPageDTO;
import com.wanwan.model.dto.UserPasswordDTO;
import com.wanwan.model.dto.UserRegisterDTO;
import com.wanwan.model.entity.Menu;
import com.wanwan.model.entity.User;
import com.wanwan.service.IMenuService;
import com.wanwan.service.IUserService;
import com.wanwan.utils.JWTUtils;
import com.wanwan.utils.MyUtil;
import com.wanwan.utils.RedisUtil;
import com.wanwan.model.vo.UserLoginVO;
import com.wanwan.model.vo.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanwan
 * @since 2024-01-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();
    private String defaultAvatarUrl;
    @Value("${server.ip}")
    private String serverIp;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private IMenuService menuService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        defaultAvatarUrl = "http://" + serverIp + ":9090/api/files/b4b86bb7e08f4876a3cd400f8220b6f6.jpeg";
    }
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        log.info("用户开始登录: {}", userLoginDTO.getUsername());
        // 1. 查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", userLoginDTO.getUsername()));
        if (user == null) {
            log.warn("用户不存在: {}", userLoginDTO.getUsername());
            throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 2. 验证密码
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            log.warn("密码错误: {}", userLoginDTO.getUsername());
            throw new ServiceException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 3. 更新登录时间
        user.setRecentlyLanded(DateUtil.date());
        updateById(user);
        // 4. 构建响应 DTO
        UserLoginVO responseDTO = new UserLoginVO();
        BeanUtil.copyProperties(user, responseDTO,true);

        // 5. 生成 Token
        Map<String, String> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        String token = JWTUtils.genToken(claims);
        responseDTO.setToken(token);

        // 6. 获取菜单信息
        List<Menu> roleMenus = getRoleMenus(user.getRole());
        responseDTO.setMenus(roleMenus);

        log.info("用户登录成功: {}", user.getUsername());
        return responseDTO;
    }

    @Override
    public UserRegisterVO register(UserRegisterDTO userRegisterDTO) {
        User existingUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", userRegisterDTO.getUsername()));
        if (existingUser != null) {
            log.warn("尝试注册已存在的用户名：{}", userRegisterDTO.getUsername());
            throw new ServiceException(ResultCodeEnum.USER_EXIT_ERROR);
        }
        // 映射 DTO 到 Entity
        User user = BeanUtil.copyProperties(userRegisterDTO, User.class);
        // 设置默认昵称
        user.setNickname("游客" + MyUtil.generateRandomString());
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置默认头像
        user.setAvatarUrl(defaultAvatarUrl);
        // 保存用户
        save(user);
        // 构建返回结果
        UserRegisterVO userRegisterVO = new UserRegisterVO();
        userRegisterVO.setNickname(user.getNickname());
        userRegisterVO.setAvatarUrl(defaultAvatarUrl);
        userRegisterVO.setCreateTime(user.getCreateTime());
        return userRegisterVO;
    }

    @Override
    public Map<String, Object> pageUserByCondition(UserPageDTO userPageDTO) {
        Integer pageNum = userPageDTO.getPageNum();
        Integer pageSize = userPageDTO.getPageSize();
        // 记录进入方法的日志
        log.info("进入分页查询用户方法，请求参数：{}", userPageDTO);
        // 构建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map.of(
                "username", userPageDTO.getUsername(),
                "nickname", userPageDTO.getNickname(),
                "address", userPageDTO.getAddress(),
                "phone", userPageDTO.getPhone(),
                "email", userPageDTO.getEmail()
        ).forEach((column, value) -> {
            if (StringUtils.isNotBlank((CharSequence) value)) {
                log.debug("添加模糊查询条件：{} 包含 '{}'", column, value);
                queryWrapper.like(column, value);
            }
        });
        queryWrapper.orderByDesc("id");

        // 使用 MyBatis Plus 的 Page 进行分页查询
        IPage<User> page = new Page<>(pageNum, pageSize);
        long startTime = System.currentTimeMillis();
        log.info("开始执行数据库查询...");

        IPage<User> resultPage = userMapper.selectPage(page, queryWrapper);
        long endTime = System.currentTimeMillis();
        // 记录查询耗时
        log.info("数据库查询完成，耗时 {} 毫秒，共查询到 {} 条数据", (endTime - startTime), resultPage.getTotal());
        // 组装返回结果
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("records", resultPage.getRecords());     // 当前页数据
        dataMap.put("total", resultPage.getTotal());         // 总记录数
        dataMap.put("pageNum", pageNum);
        dataMap.put("pageSize", pageSize);
        dataMap.put("pages", resultPage.getPages());         // 总页数
        log.info("分页数据组装完成，当前页返回 {} 条数据", resultPage.getRecords().size());
        return dataMap;
    }

    @Override
    public User queryUser(String username) {
        log.info("查询用户信息，用户名：{}", username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean saveUser(User user) {
        log.info("保存用户信息，用户名：{}", user.getUsername());
        return save(user);
    }

    @Override
    public int updateUser(User user) {
        log.info("更新用户信息，用户名：{}", user.getUsername());
        return userMapper.updateById(user);
    }

    @Override
    public boolean bindEmail(String userId, String email, String code) {
        log.info("用户绑定邮箱，用户ID：{}，邮箱：{}，验证码：{}", userId, email, code);
        String emailCodeKey = "email_code:" + email;
        String emailCode = RedisUtil.get(emailCodeKey, String.class);

        if (code.equals(emailCode)) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",userId);
            updateWrapper.set("email",email);
            return update(updateWrapper);
        } else {
            throw new ServiceException(ResultCodeEnum.USER_EMAIL_CODE_ERROR);
        }
    }

    @Override
    public int updatePassword(UserPasswordDTO userPasswordDTO) {
        log.info("用户修改密码，用户名：{}", userPasswordDTO.getUsername());
        int update = userMapper.updatePWByUN(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }else {
            return update;
        }
    }

    /**
     * 获取当前角色的菜单列表
     * @param role
     * @return
     */
    public List<Menu> getRoleMenus(String role){
        Integer roleId = roleMapper.selectRoleIdByFlag(role);

        List<Integer> menuIds = roleMenuMapper.selectRMByRoleId(roleId);
        // 查出所有菜单
        List<Menu> menus = menuService.listMenu("");
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户菜单
        for(Menu menu: menus){

            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
