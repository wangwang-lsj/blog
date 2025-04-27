package com.wanwan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanwan.annotation.AuthAccess;
import com.wanwan.common.Result;
import com.wanwan.entity.Slideshow;
import com.wanwan.service.IArticleService;
import com.wanwan.service.ISlideshowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author：玩玩
 * @since：2024/3/12 17:59
 * @description:
 */
@RestController
@RequestMapping("/api/admin/home")
public class HomeController {
    @Resource
    ISlideshowService slideshowService;
    @Resource
    IArticleService articleService;
    @AuthAccess
    @GetMapping("/slider")
    public Result getAll(@RequestHeader(name = "Wan-Source", required = false) String WanSource){
        QueryWrapper<Slideshow> queryWrapper = new QueryWrapper<>();
        if (Objects.equals(WanSource, "manage")) {
            return Result.success(slideshowService.listSlider());
        }
        queryWrapper.eq("enable", true);
        return Result.success(slideshowService.listEnableSlider());
    }
    @AuthAccess
    @GetMapping("/articles")
    public Result getHomeArticles(){
        return Result.success(articleService.listHomeArticle());
    }
    @PostMapping("/slider")
    public Result create(@RequestBody Slideshow slideshow){
        return Result.success(slideshowService.saveSlider(slideshow));
    }
    @PutMapping("/slider")
    public Result modify(@RequestBody Slideshow slideshow){
        return Result.success(slideshowService.updateSlider(slideshow));
    }
    @DeleteMapping("/slider/{id}")
    public Result deleteById(@PathVariable Integer id){
        return Result.success(slideshowService.removeSlider(id));
    }
    @DeleteMapping("/slider")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(slideshowService.removeSliders(ids));
    }
    @PatchMapping("/slider")
    public Result updateShow(@RequestBody Slideshow slideshow){
        return Result.success(slideshowService.updateSliderEnable(slideshow));
    }
}
