package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.ArticleVo;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.ArticleParam;
import com.lcq.wre.dto.param.FavoritesParam;
import com.lcq.wre.dto.param.PageParams;
import com.lcq.wre.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {Swagger2Config.Article_Controller} )
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Resource
    ArticleService articleService;
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @ApiOperation("获取首页文章列表")
    @PostMapping
    public Result listArticles(@RequestBody PageParams pageParams) {
        return articleService.listArticles(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @ApiOperation("获取最热文章列表")
    @PostMapping("hot")
    public Result hotArticles() {
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @ApiOperation("获取最新文章列表")
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
    /**
     * 首页 文章归档
     * @return
     */
    @ApiOperation("获取归档文章列表")
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @ApiOperation("获取文章详情")
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") @ApiParam("文章id") Long id) {  //获取到路径{}里的参数
        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @ApiOperation("发布文章")
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    @ApiOperation("根据文章id查询文章")
    @PostMapping("{id}")
    public Result update(@PathVariable("id") @ApiParam("文章id") Long id){
        return Result.success(articleService.findArticleById(id));
    }

    /**
     * 删除对应文章
     */
    @ApiOperation("删除指定文章")
    @PostMapping("del/{id}")
    public Result delete(@PathVariable("id")@ApiParam("文章id")Long id){
        return articleService.delete(id);
    }

    /**
     * 文章置顶
     */
    @ApiOperation("设置文章为置顶")
    @PostMapping("top/{id}")
    public Result setTop(@PathVariable("id")@ApiParam("文章id") Long id){
        return articleService.setTop(id);
    }
    /**
     * 查询收藏夹文章列表
     */
    @ApiOperation("获取用户的收藏夹")
    @GetMapping("fav/{id}")
    public Result favorites(@PathVariable("id")@ApiParam("用户id") String id){
        return articleService.getFavoritesArticle(id);
    }

    /**
     * 查询某篇文章是否被收藏
     */
    @ApiOperation("查询某文章是否被某用户收藏")
    @PostMapping("fav/query")
    public Result getIsFavorites(@RequestBody FavoritesParam favoritesParam){
        System.out.println("文章id："+favoritesParam.getArticleId());
        return articleService.getIsFavorites(favoritesParam);
    }
    /**
     * 收藏文章
     */
    @ApiOperation("收藏文章")
    @PostMapping("fav/add")
    public Result insertFavorites(@RequestBody FavoritesParam favoritesParam){
        return articleService.addFavoritesArticle(favoritesParam);
    }
    /**
     * 取消收藏文章
     */
    @ApiOperation("取消收藏文章")
    @PostMapping("fav/del")
    public Result delFavorites(@RequestBody FavoritesParam favoritesParam){
        return articleService.delFavoritesArticle(favoritesParam);
    }
}
