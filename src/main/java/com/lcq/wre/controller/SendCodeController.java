package com.lcq.wre.controller;

import com.lcq.wre.config.Swagger2Config;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CodeParam;
import com.lcq.wre.dto.param.ResetPwdParam;
import com.lcq.wre.service.LoginService;
import com.lcq.wre.service.SendMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {Swagger2Config.SendCode_Controller})
@RestController
@RequestMapping("sendCode")
public class SendCodeController {
    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private LoginService loginService;

    @PostMapping("send")
    @ApiOperation("发送找回密码的邮件验证码")
//    @LogAnnotation(module = "发送",operation = "找回密码的邮件验证码")
    public Result sendCode(@RequestBody CodeParam codeParam){
        return sendMsgService.sendCodeToEmail(codeParam);
    }

    @PostMapping("reg")
    @ApiOperation("发送注册的邮件验证码")
//    @LogAnnotation(module = "发送",operation = "注册的邮件验证码")
    public Result sendCodeREG(@RequestBody CodeParam codeParam){
        return sendMsgService.sendCodeRegister(codeParam);
    }

    @PostMapping("resetPwd")
    @ApiOperation("发送重置密码的邮件验证码")
//    @LogAnnotation(module = "发送",operation = "重置密码")
    public Result resetPwd(@RequestBody ResetPwdParam resetPwdParam){
        return loginService.resetPassWord(resetPwdParam);
    }
}