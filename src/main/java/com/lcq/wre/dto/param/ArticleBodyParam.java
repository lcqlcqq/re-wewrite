package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleBodyParam {

    @ApiModelProperty(value = "文章内容")
    private String content;
    @ApiModelProperty(value = "文章html代码")
    private String contentHtml;

}
