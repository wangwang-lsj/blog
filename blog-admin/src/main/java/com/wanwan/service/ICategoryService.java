package com.wanwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanwan.entity.Category;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanwan
 * @since 2024-03-07
 */
public interface ICategoryService extends IService<Category> {

    boolean saveCategory(Category category);

    int updateCategory(Category category);
}
