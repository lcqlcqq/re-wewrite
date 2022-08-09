package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CodeParam {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "验证码代码")
    private String code;
}
