package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryParam {
    private String id;
    @ApiModelProperty(value = "分类图标")
    private String avatar;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "描述")
    private String description;
}
