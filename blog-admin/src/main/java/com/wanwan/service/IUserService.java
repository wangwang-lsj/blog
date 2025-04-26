package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.entity.User;
import com.wanwan.dto.UserDTO;
import com.wanwan.dto.UserPasswordDTO;

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

    UserDTO login(UserDTO userDto);

    User register(UserDTO userDto);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Map<String,Object> findByPageOrSearch(Integer pageNum, Integer pageSize, String username, String nickname, String address, String phone, String email);
}
