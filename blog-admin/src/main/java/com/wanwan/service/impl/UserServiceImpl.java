package com.wanwan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanwan.common.enums.ResultCodeEnum;
import com.wanwan.dto.*;
import com.wanwan.exception.ServiceException;
import com.wanwan.mapper.RoleMapper;
import com.wanwan.mapper.RoleMenuMapper;
import com.wanwan.mapper.UserMapper;
import com.wanwan.entity.Menu;
import com.wanwan.entity.User;
import com.wanwan.service.IMenuService;
import com.wanwan.service.IUserService;
import com.wanwan.utils.JWTUtils;
import com.wanwan.utils.MyUtil;
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
import java.util.stream.Collectors;

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
    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
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
        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
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
    public User register(UserRegisterDTO userRegisterDTO) {
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
        return user;
    }

    @Override
    public Map<String, Object> pageUserByCondition(Integer pageNum, Integer pageSize, String username, String nickname, String address, String phone, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if(!"".equals(phone)){
            queryWrapper.like("phone",phone);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        queryWrapper.orderByDesc("id");
        List<User> list = list(queryWrapper);
        Map<String,Object> dataMap = new HashMap<>();
        // codeUseList：处理后的所有符合条件的数据（list）
        // 组装返回结果对象 list：当前页数据列表 total：数据总数
        dataMap.put("records", list.stream().skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize).collect(Collectors.toList()));
        dataMap.put("total", list.size());
        return dataMap;
    }

    @Override
    public User queryUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User one = userMapper.selectOne(queryWrapper);
        return one;
    }

    @Override
    public boolean saveUser(User user) {
        return save(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public boolean bindEmail(String userId, String email) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId);
        updateWrapper.set("email",email);
        return update(updateWrapper);
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePWByUN(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
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
