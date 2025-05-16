package com.wanwan.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.annotation.AuthAccess;
import com.wanwan.response.Result;
import com.wanwan.model.entity.Category;
import com.wanwan.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;

    @AuthAccess
    @GetMapping()
    public Result<List<Category>> queryAll() {
        return Result.success(categoryService.list());
    }

    // @GetMapping("/{id}")
    // public Result queryById(@PathVariable Integer id) {
    //     return Result.success(categoryService.getById(id));
    // }

    @GetMapping ("/page")
    public Result<IPage<Category>> queryPage(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam String name
                       ) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.eq("name", name);
        }
        return Result.success(categoryService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    @PostMapping
    public Result<Boolean> create(@RequestBody Category category){
        return Result.success(categoryService.saveCategory(category));
    }
    // 新增或者更新
    @PutMapping
    public Result<Integer> modify(@RequestBody Category category) {
        return Result.success(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return Result.success("");
    }

    @DeleteMapping()
    public Result<String> deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.removeByIds(ids);
        return Result.success("");
    }



}

