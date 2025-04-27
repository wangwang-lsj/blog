package com.wanwan.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.common.Result;
import com.wanwan.entity.Role;
import com.wanwan.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {
    @Resource
    private IRoleService roleService;

    /**
     * 角色分页
     * @param pageNum
     * @param pageSize
     * @param name
     * @return Page
     */
    @GetMapping("/page")
    public Result queryPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String name

    ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 获取所有角色
     * @return List<Role>
     */
    @GetMapping("")
    public Result queryRoles() {
        return Result.success(roleService.list());
    }

    /**
     * 新增角色
     * @param role
     * @return Boolean
     */
    @PostMapping("")
    public Result create(@RequestBody Role role) {
        return Result.success(roleService.saveRole(role));
    }
    /**
     * 更新角色
     * @param role
     * @return Boolean
     */
    @PutMapping("")
    public Result modify(@RequestBody Role role) {
        return Result.success(roleService.updateRole(role));
    }
    /**
     * 删除角色
     * @param id
     * @return Boolean
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    /**
     * 删除多个角色
     * @param ids
     * @return Boolean
     */
    @DeleteMapping("")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeByIds(ids));
    }



    /**
     * 获取角色对应的菜单
     * @param roleId
     * @return List<Integer>
     */
    @GetMapping("/{roleId}/menus")
    public Result queryMenuById(@PathVariable Integer roleId) {
        return Result.success(roleService.listRoleMenu(roleId));
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return Boolean
     */
    @PostMapping("/{roleId}/menus")
    public Result createRoleMenus(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.updateRoleMenu(roleId, menuIds);
        return Result.success();
    }

}

