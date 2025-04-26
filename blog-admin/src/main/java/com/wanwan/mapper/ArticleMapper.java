package com.wanwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 文章(博客） Mapper 接口
 * </p>
 *
 * @author wanwan
 * @since 2024-03-08
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    Page<Article> selectAllByPage(Page<Article> page, @Param("title") String title,@Param("description") String description,@Param("userName") String userName,@Param("categoryName") String categoryName, @Param("orderTarget") String orderTarget,@Param("order") String order);

    Article getOneAllById(@Param("id")Integer id);


    @Update("update article set likes = likes + #{num} where id = #{id}")
    Boolean updateArticleLikes(@Param("id")Integer id,@Param("num")Integer num);

    @Update("update article set read_count = read_count + #{num} where id = #{id}")
    Boolean updateArticleReadCount(@Param("id")Integer id,@Param("num")Integer num);
}
