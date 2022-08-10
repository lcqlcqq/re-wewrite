package com.lcq.wre.service;

import com.lcq.wre.dao.pojo.User;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.LoginEmailParam;
import com.lcq.wre.dto.param.LoginParam;
import com.lcq.wre.dto.param.ResetPwdParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional  //事务回滚
public interface LoginService {
    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 登出
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);

    User checkToken(String token);

    /**
     * 用户邮箱登录
     * @param loginEmailParam
     * @return
     */
    Result loginByEmail(LoginEmailParam loginEmailParam);

    /**
     * 验证邮箱是否存在
     */
    String checkMail(String mail);

    /**
     * 重置密码
     * @param resetPwdParam
     * @return
     */
    Result resetPassWord(ResetPwdParam resetPwdParam);
}
