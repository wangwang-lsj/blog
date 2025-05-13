package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.dto.*;
import com.wanwan.entity.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanwan
 * @since 2024-01-22
 */
public interface IUserService extends IService<User> {

    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);

    User register(UserRegisterDTO userRegisterDTO);
    // User registerByVistor();
    // User registerByEmail();
    // User registerByPhone();


    void updatePassword(UserPasswordDTO userPasswordDTO);

    Map<String,Object> pageUserByCondition(UserPageDTO userPageDTO);
    User queryUser(String username);
    boolean saveUser(User user);

    int updateUser(User user);

    boolean bindEmail(String userId, String email);
}
