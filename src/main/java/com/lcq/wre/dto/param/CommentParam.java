package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentParam {
    @ApiModelProperty(value = "所属文章")
    private Long articleId;
    @ApiModelProperty(value = "评论")
    private String content;
    @ApiModelProperty(value = "父评论id")
    private Long parent;
    @ApiModelProperty(value = "评论对象")
    private Long toUserId;
}
