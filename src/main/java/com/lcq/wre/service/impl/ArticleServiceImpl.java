package com.lcq.wre.service.impl;

import com.lcq.wre.dto.ArticleVo;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.ArticleParam;
import com.lcq.wre.dto.param.FavoritesParam;
import com.lcq.wre.dto.param.PageParams;
import com.lcq.wre.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public Result listArticles(PageParams pageParams) {
//        Page<WrArticle> page = new Page<>();
//        IPage<WrArticle> articleIPage = this.articleMapper.listArticle(
//                page,
//                pageParams.getCategoryId(),
//                pageParams.getTagId(),
//                pageParams.getYear(),
//                pageParams.getMonth());
//        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    @Override
    public Result hotArticles(int limit) {
        return null;
    }

    @Override
    public Result newArticles(int limit) {
        return null;
    }

    @Override
    public Result listArchives() {
        return null;
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        return null;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        return null;
    }

    @Override
    public Result delete(Long id) {
        return null;
    }

    @Override
    public Result setTop(Long id) {
        return null;
    }

    @Override
    public Result getFavoritesArticle(String userId) {
        return null;
    }

    @Override
    public Result addFavoritesArticle(FavoritesParam favoritesParam) {
        return null;
    }

    @Override
    public Result getIsFavorites(FavoritesParam favoritesParam) {
        return null;
    }

    @Override
    public Result delFavoritesArticle(FavoritesParam favoritesParam) {
        return null;
    }
}
