package com.wanwan.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.wanwan.annotation.AuthAccess;
import com.wanwan.model.dto.UserLoginDTO;
import com.wanwan.model.dto.UserPageDTO;
import com.wanwan.model.dto.UserPasswordDTO;
import com.wanwan.model.dto.UserRegisterDTO;
import com.wanwan.response.Result;
import com.wanwan.model.dto.*;
import com.wanwan.model.entity.User;
import com.wanwan.service.IUserService;
import com.wanwan.utils.JWTUtils;
import com.wanwan.model.vo.UserLoginVO;
import com.wanwan.model.vo.UserRegisterVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-01-22
 */
@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return user
     */
    @AuthAccess
    @PostMapping("/register")
    public Result<UserRegisterVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return Result.success(userService.register(userRegisterDTO));
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return userDTO
     */
    @AuthAccess
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserLoginVO dto = userService.login(userLoginDTO);
        return Result.success(dto);
    }
    @PostMapping("/bindemail")
    public Result<Boolean> bindEmail(HttpServletRequest request, @RequestParam String email, @RequestParam String code) {
        String userId = JWTUtils.getUserIdFromRequest(request);
        return Result.success(userService.bindEmail(userId,email,code));
    }

    /**
     * 按条件分页查询
     */
    @GetMapping("/page")
    public Result<Map<String,Object>> queryPage(@ModelAttribute @Valid UserPageDTO userPageDTO){
        return Result.success(userService.pageUserByCondition(userPageDTO));
    }

    /**
     * 新增用户
     * @param user
     * @return Boolean
     */
    @PostMapping("")
    public Result<Boolean> create(@RequestBody User user) {
        return Result.success(userService.saveUser(user));
    }
    /**
     * 修改用户
     * @param user
     * @return Boolean
     */
    @PutMapping("")
    public Result<Integer> modify(@RequestBody User user) {
        return Result.success(userService.updateUser(user));
    }
    /**
     * 通过id删除
     * @param id
     * @return Boolean
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteById(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    /**
     * 通过ids批量删除
     * @param ids
     * @return Boolean
     */
    @DeleteMapping("")
    public Result<Boolean> deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return 异常返回数据
     */
    @PatchMapping("")
    public Result<Integer> modifyPassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    /**
     * 通过用户名查找用户
     * @param username
     * @return user
     */
    @GetMapping("/{username}")
    public Result<User> queryByName(@PathVariable String username) {
        return Result.success(userService.queryUser(username));
    }


    /**
     * 查询一个角色的所有的用户
     * @param role
     * @return List<User>
     */
    // @GetMapping("/user/role/{role}")
    // public Result selectUserByRole(@PathVariable String role) {
    //     QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    //     queryWrapper.eq("role", role);
    //     return Result.success(userService.list(queryWrapper));
    // }


    /**
     * 导出用户表为excel
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        List<User> list = userService.list();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        IoUtil.close(out);
        writer.close();
    }

    /**
     * 将excel中用户导入
     * @param file
     * @return Boolean
     * @throws Exception
     */
    @PostMapping("/import")
    public Result<Boolean> importExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.read();
        // 要求excel表头英文
        // List<User> list = reader.readAll(User.class);
        // 表头中文
        List<List<Object>> lists = reader.read(1);
        List<User> userList = new ArrayList<>();
        for (List<Object> list : lists) {
            User user = new User();
            user.setUsername(list.get(0).toString());
            user.setPassword(list.get(1).toString());
            user.setNickname(list.get(2).toString());
            user.setEmail(list.get(3).toString());
            user.setPhone(list.get(4).toString());
            user.setAddress(list.get(5).toString());
            user.setAvatarUrl(list.get(6).toString());
            userList.add(user);
        }
        return Result.success(userService.saveBatch(userList));
    }


}

