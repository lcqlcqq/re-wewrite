package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginEmailParam {
    @ApiModelProperty(value = "账户绑定的邮箱")
    private String email;
    @ApiModelProperty(value = "密码")
    private String password;
}
