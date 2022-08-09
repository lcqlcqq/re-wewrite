package com.lcq.wre.dto.param;

import com.quan.wewrite.vo.CategoryVo;
import com.quan.wewrite.vo.TagVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;
    @ApiModelProperty(value = "文章体")
    private ArticleBodyParam body;
    @ApiModelProperty(value = "文章分类")
    private CategoryVo category;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "文章标签")
    private List<TagVo> tags;
    @ApiModelProperty(value = "文章标题")
    private String title;
}
