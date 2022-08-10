package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.LoginEmailParam;
import com.lcq.wre.dto.param.LoginParam;
import com.lcq.wre.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {Swagger2Config.Login_Controller})
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping
//    @LogAnnotation(module = "登录",operation = "用户账号登录")
    @ApiOperation("用户账号登录")
    public Result login(@RequestBody LoginParam loginParam){

        return loginService.login(loginParam);
    }
    @PostMapping("mail")
//    @LogAnnotation(module = "登录",operation = "用户邮箱登录")
    @ApiOperation("用户邮箱登录")
    public Result loginByEmail(@RequestBody LoginEmailParam loginEmailParam){
        return loginService.loginByEmail(loginEmailParam);
    }


}
