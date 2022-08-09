package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TagParam {

    private String id;
    @ApiModelProperty(value = "图标")
    private String avatar;
    @ApiModelProperty(value = "标签名称")
    private String tagName;
}
