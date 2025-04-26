package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> selectMenus(String name);
}
