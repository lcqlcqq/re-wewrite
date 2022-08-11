package com.lcq.wre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcq.wre.dao.dos.Archives;
import com.lcq.wre.dao.mapper.ArticleBodyMapper;
import com.lcq.wre.dao.mapper.ArticleMapper;
import com.lcq.wre.dao.mapper.ArticleTagMapper;
import com.lcq.wre.dao.mapper.FavoritesMapper;
import com.lcq.wre.dao.pojo.*;
import com.lcq.wre.dto.*;
import com.lcq.wre.dto.param.ArticleParam;
import com.lcq.wre.dto.param.FavoritesParam;
import com.lcq.wre.dto.param.PageParams;
import com.lcq.wre.service.*;
import com.lcq.wre.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${redis.key.expire.article}")
    private Long ARTICLE_EXPIRE;
    @Value("${redis.key.prefix.article}")
    private String REDIS_KEY_PREFIX_ARTICLE;
    @Value("${redis.key.expire.tag}")
    private Long TAG_EXPIRE;
    @Value("${redis.key.prefix.tag}")
    private String REDIS_KEY_PREFIX_TAG;
    @Value("${redis.key.expire.category}")
    private Long CATEGORY_EXPIRE;
    @Value("${redis.key.prefix.category}")
    private String REDIS_KEY_PREFIX_CATEGORY;

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Autowired
    ArticleBodyMapper articleBodyMapper;
    @Autowired
    FavoritesMapper favoritesMapper;

    @Autowired
    RedisService redisService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;
    @Autowired
    CommentsService commentsService;

    @Override
    public Result listArticles(PageParams pageParams) {
        Page<Article> page = new Page<>();
        IPage<Article> articleIPage = this.articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select * id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public ArticleVo findArticleById(Long id) {
        /**
         * 1. 根据id查询文章
         * 2. 根据bodyid和categoryid 关联查询
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //更新阅读量，更新数据库是加写锁的，会阻塞读操作，性能就会比较低
        //更新增加了这个接口的耗时。一旦更新出问题，不能影响查看文章的操作
        //线程池，另起线程处理更新
//        threadService.updateViewCount(articleMapper, article);
        return articleVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1、发布文章 目的 构建Article对象
         * 2、作者id是当前登录用户的
         * 3、如果文章id已经存在了，就是更新操作，保留几个旧数据，然后先删除3个表的内容
         * 4、如果不是更新操作，就直接插入了。
         * 5、插入标签列表。
         * 6、插入文章内容。
         */
        //此接口加入登录拦截器
        User user = UserThreadLocal.get();

        Long articleId = articleParam.getId();
        //查到旧的文章记录
        Article a = articleMapper.selectById(articleId);
        //新的文章
        Article article = new Article();
        article.setAuthorId(user.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        //如果旧文章存在，那么是更新操作
        if (a != null) {
            //要保留几个旧的文章数据
            Integer viewCounts = a.getViewCounts();
            Integer commentCounts = a.getCommentCounts();
            Integer weight = a.getWeight();
            //id、阅读量、评论数、置顶
            article.setId(articleId);
            article.setViewCounts(viewCounts);
            article.setCommentCounts(commentCounts);
            article.setWeight(weight);

            //删除旧的文章内容
            LambdaQueryWrapper<ArticleBody> queryBody = new LambdaQueryWrapper<>();
            queryBody.eq(ArticleBody::getArticleId, articleId);
            Long BodyId = articleBodyMapper.selectOne(queryBody).getId();
            articleBodyMapper.deleteById(BodyId);
            //删除旧的文章所有标签
            LambdaQueryWrapper<ArticleTag> queryTag = new LambdaQueryWrapper<>();
            queryTag.eq(ArticleTag::getArticleId, articleId);
            queryTag.select(ArticleTag::getId);
            List<ArticleTag> TagItems = articleTagMapper.selectList(queryTag);
            List<Long> TagIds = new ArrayList<>();
            for (ArticleTag at : TagItems) {
                TagIds.add(at.getId());
            }
            articleTagMapper.deleteBatchIds(TagIds);
            //删除旧的文章记录
            articleMapper.deleteById(articleId);
        }

        //插入之后回生成一个文章id
        this.articleMapper.insert(article);

        //tags
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                this.articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        //ArticleVo articleVo = new ArticleVo();
        //articleVo.setId(article.getId());
        //return Result.success(articleVo);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());

        //删除文章缓存
//        Set<String> keys = redisService.keys("article_" + "*");
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("tags_" + "*")));
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("Categories_" + "*")));
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*"
                , REDIS_KEY_PREFIX_TAG+"*"
                , REDIS_KEY_PREFIX_CATEGORY+"*");

        //删除缓存失败了
        if (redisService.remove(keys) <= 0) {
//            rocketMQTemplate.convertAndSend("blog-deleteArticle-failed", keys);
        }
        return Result.success(map);
    }

    @Override
    public Result delete(Long articleId) {
        //删除文章内容
        LambdaQueryWrapper<ArticleBody> queryBody = new LambdaQueryWrapper<>();
        queryBody.eq(ArticleBody::getArticleId, articleId);
        Long BodyId = articleBodyMapper.selectOne(queryBody).getId();
        int abmd = articleBodyMapper.deleteById(BodyId);
        //删除文章所有标签记录
        LambdaQueryWrapper<ArticleTag> queryTag = new LambdaQueryWrapper<>();
        queryTag.eq(ArticleTag::getArticleId, articleId);
        queryTag.select(ArticleTag::getId);
        List<ArticleTag> TagItems = articleTagMapper.selectList(queryTag);
        List<Long> TagIds = new ArrayList<>();
        for (ArticleTag at : TagItems) {
            TagIds.add(at.getId());
        }
        int atmd = articleTagMapper.deleteBatchIds(TagIds);
        //删除文章记录
        int amd = articleMapper.deleteById(articleId);
        //删除文章所有评论
        Integer com = commentsService.deleteComment(articleId);
        //删除文章缓存
//        Set<String> keys = redisTemplate.keys("article_" + "*");
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("tags_" + "*")));
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("Categories_" + "*")));
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*"
                , REDIS_KEY_PREFIX_TAG+"*"
                , REDIS_KEY_PREFIX_CATEGORY+"*");
        //删除缓存失败了
        if (redisService.remove(keys) <= 0) {
//            rocketMQTemplate.convertAndSend("blog-deleteArticle-failed", keys);
        }
        return Result.success(amd);
    }

    @Override
    public Result setTop(Long id) {
        Article article = articleMapper.selectById(id);
        if (article.getWeight() == 0)
            article.setWeight(1);
        else
            article.setWeight(0);
        int updateRes = articleMapper.updateById(article);
        //只删除文章列表相关的缓存
//        Set<String> keys = redisTemplate.keys("article_" + "*");
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*");
        //删除缓存失败了
        if (redisService.remove(keys) <= 0) {
//            rocketMQTemplate.convertAndSend("blog-deleteArticle-failed", keys);
        }
        return Result.success(updateRes);
    }

    @Override
    public Result getFavoritesArticle(String userId) {
        List<Article> articleList = articleMapper.listFavorites(Long.parseLong(userId));
        return Result.success(copyList(articleList, true, true));
    }

    @Override
    public Result addFavoritesArticle(FavoritesParam favoritesParam) {
        int insert = favoritesMapper.insert(new Favorites(Long.parseLong(favoritesParam.getUserId()), Long.parseLong(favoritesParam.getArticleId())));
        if (insert < 1) return Result.fail(-1, "收藏失败");
        return Result.success("收藏成功");
    }

    @Override
    public Result getIsFavorites(FavoritesParam favoritesParam) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getArticleId, favoritesParam.getArticleId());
        queryWrapper.eq(Favorites::getUserId, favoritesParam.getUserId());
        Integer integer = favoritesMapper.selectCount(queryWrapper);
        if (integer < 1) return Result.fail(0, "无收藏");
        return Result.success(integer);
    }

    @Override
    public Result delFavoritesArticle(FavoritesParam favoritesParam) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getArticleId, favoritesParam.getArticleId());
        queryWrapper.eq(Favorites::getUserId, favoritesParam.getUserId());
        int integer = favoritesMapper.delete(queryWrapper);
        if (integer < 1) return Result.fail(0, "取消收藏失败");
        return Result.success("取消收藏成功");
    }


    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.findSysUserById(authorId).getNickname());
        }
        if (isBody) {
            ArticleBodyVo articleBody = findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }
        if (isCategory) {
            CategoryVo categoryVo = findCategory(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }


    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, articleId);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
