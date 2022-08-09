package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.LoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {Swagger2Config.Register_Controller})
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    /**
     * 注册
     * @param loginParam
     * @return
     */
    @PostMapping
    @ApiOperation("用户注册")
    @LogAnnotation(module = "注册",operation = "用户注册")
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
