package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.model.dto.*;
import com.wanwan.model.dto.UserLoginDTO;
import com.wanwan.model.dto.UserPageDTO;
import com.wanwan.model.dto.UserPasswordDTO;
import com.wanwan.model.dto.UserRegisterDTO;
import com.wanwan.model.entity.User;
import com.wanwan.model.vo.UserLoginVO;
import com.wanwan.model.vo.UserRegisterVO;

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

    UserLoginVO login(UserLoginDTO userLoginDTO);

    UserRegisterVO register(UserRegisterDTO userRegisterDTO);
    // User registerByVistor();
    // User registerByEmail();
    // User registerByPhone();


    int updatePassword(UserPasswordDTO userPasswordDTO);

    Map<String,Object> pageUserByCondition(UserPageDTO userPageDTO);
    User queryUser(String username);
    boolean saveUser(User user);

    int updateUser(User user);

    boolean bindEmail(String userId, String email, String code);
}
