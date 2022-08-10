package com.lcq.wre.service;

import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 发表评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);

    /**
     * 删除文章下的评论
     * @param articleId
     * @return
     */
    Integer deleteComment(Long articleId);
}
