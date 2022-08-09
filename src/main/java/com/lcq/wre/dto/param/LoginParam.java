package com.lcq.wre.dto.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginParam {
    @ApiModelProperty(value = "账户账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "别称")
    private String nickname;
    @ApiModelProperty(value = "邮箱")
    private String email;
}
