package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FavoritesParam {
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "收藏的文章id")
    private String articleId;

}
