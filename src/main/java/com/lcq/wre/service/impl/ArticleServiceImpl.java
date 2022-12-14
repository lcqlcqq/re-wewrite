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
         * 1. ??????id????????????
         * 2. ??????bodyid???categoryid ????????????
         */
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        //?????????????????????????????????????????????????????????????????????????????????????????????
        //????????????????????????????????????????????????????????????????????????????????????????????????
        //????????????????????????????????????
//        threadService.updateViewCount(articleMapper, article);
        return articleVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1??????????????? ?????? ??????Article??????
         * 2?????????id????????????????????????
         * 3???????????????id??????????????????????????????????????????????????????????????????????????????3???????????????
         * 4???????????????????????????????????????????????????
         * 5????????????????????????
         * 6????????????????????????
         */
        //??????????????????????????????
        User user = UserThreadLocal.get();

        Long articleId = articleParam.getId();
        //????????????????????????
        Article a = articleMapper.selectById(articleId);
        //????????????
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
        //?????????????????????????????????????????????
        if (a != null) {
            //?????????????????????????????????
            Integer viewCounts = a.getViewCounts();
            Integer commentCounts = a.getCommentCounts();
            Integer weight = a.getWeight();
            //id?????????????????????????????????
            article.setId(articleId);
            article.setViewCounts(viewCounts);
            article.setCommentCounts(commentCounts);
            article.setWeight(weight);

            //????????????????????????
            LambdaQueryWrapper<ArticleBody> queryBody = new LambdaQueryWrapper<>();
            queryBody.eq(ArticleBody::getArticleId, articleId);
            Long BodyId = articleBodyMapper.selectOne(queryBody).getId();
            articleBodyMapper.deleteById(BodyId);
            //??????????????????????????????
            LambdaQueryWrapper<ArticleTag> queryTag = new LambdaQueryWrapper<>();
            queryTag.eq(ArticleTag::getArticleId, articleId);
            queryTag.select(ArticleTag::getId);
            List<ArticleTag> TagItems = articleTagMapper.selectList(queryTag);
            List<Long> TagIds = new ArrayList<>();
            for (ArticleTag at : TagItems) {
                TagIds.add(at.getId());
            }
            articleTagMapper.deleteBatchIds(TagIds);
            //????????????????????????
            articleMapper.deleteById(articleId);
        }

        //?????????????????????????????????id
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

        //??????????????????
//        Set<String> keys = redisService.keys("article_" + "*");
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("tags_" + "*")));
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("Categories_" + "*")));
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*"
                , REDIS_KEY_PREFIX_TAG+"*"
                , REDIS_KEY_PREFIX_CATEGORY+"*");

        //?????????????????????
        if (redisService.remove(keys) <= 0) {
//            rocketMQTemplate.convertAndSend("blog-deleteArticle-failed", keys);
        }
        return Result.success(map);
    }

    @Override
    public Result delete(Long articleId) {
        //??????????????????
        LambdaQueryWrapper<ArticleBody> queryBody = new LambdaQueryWrapper<>();
        queryBody.eq(ArticleBody::getArticleId, articleId);
        Long BodyId = articleBodyMapper.selectOne(queryBody).getId();
        int abmd = articleBodyMapper.deleteById(BodyId);
        //??????????????????????????????
        LambdaQueryWrapper<ArticleTag> queryTag = new LambdaQueryWrapper<>();
        queryTag.eq(ArticleTag::getArticleId, articleId);
        queryTag.select(ArticleTag::getId);
        List<ArticleTag> TagItems = articleTagMapper.selectList(queryTag);
        List<Long> TagIds = new ArrayList<>();
        for (ArticleTag at : TagItems) {
            TagIds.add(at.getId());
        }
        int atmd = articleTagMapper.deleteBatchIds(TagIds);
        //??????????????????
        int amd = articleMapper.deleteById(articleId);
        //????????????????????????
        Integer com = commentsService.deleteComment(articleId);
        //??????????????????
//        Set<String> keys = redisTemplate.keys("article_" + "*");
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("tags_" + "*")));
//        keys.addAll(Objects.requireNonNull(redisTemplate.keys("Categories_" + "*")));
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*"
                , REDIS_KEY_PREFIX_TAG+"*"
                , REDIS_KEY_PREFIX_CATEGORY+"*");
        //?????????????????????
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
        //????????????????????????????????????
//        Set<String> keys = redisTemplate.keys("article_" + "*");
        Set<String> keys = redisService.getKeys(REDIS_KEY_PREFIX_ARTICLE+"*");
        //?????????????????????
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
        if (insert < 1) return Result.fail(-1, "????????????");
        return Result.success("????????????");
    }

    @Override
    public Result getIsFavorites(FavoritesParam favoritesParam) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getArticleId, favoritesParam.getArticleId());
        queryWrapper.eq(Favorites::getUserId, favoritesParam.getUserId());
        Integer integer = favoritesMapper.selectCount(queryWrapper);
        if (integer < 1) return Result.fail(0, "?????????");
        return Result.success(integer);
    }

    @Override
    public Result delFavoritesArticle(FavoritesParam favoritesParam) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getArticleId, favoritesParam.getArticleId());
        queryWrapper.eq(Favorites::getUserId, favoritesParam.getUserId());
        int integer = favoritesMapper.delete(queryWrapper);
        if (integer < 1) return Result.fail(0, "??????????????????");
        return Result.success("??????????????????");
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

        //???????????????????????? ??????????????? ???????????????
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
