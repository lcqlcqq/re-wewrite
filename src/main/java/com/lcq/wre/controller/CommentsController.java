package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CommentParam;
import com.lcq.wre.service.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(tags = {Swagger2Config.Comment_Controller})
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    CommentsService commentsService;
    /**
     * 查询文章下的评论
     * @param articleId
     * @return
     */
    @ApiOperation("查询文章下的评论")
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") @ApiParam("文章id") Long articleId){
        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 发布评论
     * @param commentParam
     * @return
     */
    @ApiOperation("发评论")
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
