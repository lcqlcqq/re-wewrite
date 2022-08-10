package com.lcq.wre.service;

import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.CodeParam;

public interface SendMsgService {
    /**
     * 发送邮件验证码
     * @param codeParam
     * @return
     */
    Result sendCodeToEmail(CodeParam codeParam);

    /**
     * 发送注册验证用的验证码
     * @param codeParam
     * @return
     */
    Result sendCodeRegister(CodeParam codeParam);
}
