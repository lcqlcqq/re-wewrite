package com.lcq.wre.controller;

import com.lcq.wre.dto.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping("currentUser")
    @ApiOperation("验证token，获取当前用户")
//    @LogAnnotation(module = "用户",operation = "获取当前用户")
    public Result currentUser(@RequestHeader("Authorization") @ApiParam("客户端JWT的token") String token){
        return sysUserService.getUserInfoByToken(token);
    }
}
