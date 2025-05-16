package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.model.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
public interface IRoleService extends IService<Role> {

    boolean updateRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> listRoleMenu(Integer roleId);
    boolean saveRole(Role role);

    int updateRole(Role role);

}
