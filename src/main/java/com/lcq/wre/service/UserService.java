package com.lcq.wre.service;

import com.lcq.wre.dao.pojo.User;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.UserVo;

import java.util.Date;

public interface UserService {

    User findSysUserById(Long id);

    User findUser(String account, String pwd);

    /**
     * 根据token查询用户信息，去redis找
     * @param token
     * @return
     */
    Result getUserInfoByToken(String token);

    /**
     * 查找用户名，注册用
     * @param account
     * @return
     */
    User findUserByAccount(String account);

    /**
     * 查询nickname是否已存在，注册用
     * @param nickname
     * @return
     */
    User findUserByNickname(String nickname);
    /**
     * 保存用户名信息，注册用
     * @param sysUser
     */
    void save(User sysUser);

    /**
     * 评论 通过id获取用户
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);

    Integer updateLastLoginTime(Date date, Long id);

}
