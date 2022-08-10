package com.lcq.wre.service.impl;

import com.lcq.wre.dto.ErrorCode;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CodeParam;
import com.lcq.wre.service.LoginService;
import com.lcq.wre.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class sendMsgServiceImpl implements SendMsgService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private LoginService loginService;

    @Override
    public Result sendCodeRegister(CodeParam codeParam) {
        String findSysUserAcc = loginService.checkMail(codeParam.getEmail());
        if(!findSysUserAcc.isEmpty()){
            return Result.fail(ErrorCode.EMAIL_ALREADY_USED.getCode(), ErrorCode.EMAIL_ALREADY_USED.getMsg());
        }
        try{
            sendRegisterMail(codeParam.getEmail(),codeParam.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success("验证码发送成功");
    }

    @Override
    public Result sendCodeToEmail(CodeParam codeParam) {
        String findSysUserAcc = loginService.checkMail(codeParam.getEmail());
        if(findSysUserAcc.isEmpty()){
            return Result.fail(ErrorCode.EMAIL_NOT_EXIST.getCode(), ErrorCode.EMAIL_NOT_EXIST.getMsg());
        }
        try{
            sendResetPwdMail(codeParam.getEmail(),codeParam.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success("验证码发送成功");
    }


    public void sendResetPwdMail(String receiver, String code) {
        System.out.println(receiver+code);
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("WeWrite");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("wewr1te@163.com");
        // 设置邮件接收者，可以有多个接收者，用逗号隔开
        message.setTo(receiver);
        // 设置邮件抄送人，可以有多个抄送人
        //message.setCc("WeWrite");
        // 设置隐秘抄送人，可以有多个
        //message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("【WeWrite】 您正在申请找回密码。您的验证码是: "+code+"，有效期5分钟。");
        // 发送邮件
        javaMailSender.send(message);
    }
    public void sendRegisterMail(String receiver, String code) {
        System.out.println(receiver+code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("WeWrite");
        message.setFrom("wewr1te@163.com");
        message.setTo(receiver);
        message.setSentDate(new Date());
        message.setText("【WeWrite】 您正在注册新账号。您的验证码是: "+code+"，有效期5分钟。");
        javaMailSender.send(message);
    }


}
