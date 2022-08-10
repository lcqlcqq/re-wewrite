package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageParams {
    @ApiModelProperty(value = "页码")
    private int page = 1;

    @ApiModelProperty(value = "每页数量")
    private int pageSize = 10;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "月份")
    private String month;

    //适配sql语句，在个位数月份号前面加个0
    public String getMonth() {
        if (this.month != null && this.month.length() == 1) {
            return "0" + this.month;
        }
        return this.month;
    }

}
