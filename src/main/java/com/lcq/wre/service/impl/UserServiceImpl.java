package com.lcq.wre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lcq.wre.dao.mapper.UserMapper;
import com.lcq.wre.dao.pojo.User;
import com.lcq.wre.dto.ErrorCode;
import com.lcq.wre.dto.LoginUserVo;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.UserVo;
import com.lcq.wre.service.LoginService;
import com.lcq.wre.service.RedisService;
import com.lcq.wre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

public class UserServiceImpl implements UserService {
    @Value("${redis.key.expire.token}")
    private Long TOKEN_EXPIRE_HOURS;
    @Value("${redis.key.prefix.token}")
    private String REDIS_KEY_PREFIX_TOKEN;

    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    private LoginService loginService;
    @Override
    public User findSysUserById(Long id) {

        User user = userMapper.selectById(id);
        if(user == null){
            user = new User();
            user.setNickname("佚名");
        }
        return user;
    }
    @Override
    public User findUser(String account, String pwd) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        queryWrapper.eq(User::getPassword,pwd);
        queryWrapper.select(User::getId,User::getAccount,User::getAvatar,User::getNickname);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Result getUserInfoByToken(String token) {
        /**
         * 1. token合法性检验：
         *      是否为空，解析是否成功，redis是否存在
         * 2. 校验失败就报错；校验成功就返回对应结果
         */
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.LOGIN_STATE_ERROR.getCode(),ErrorCode.LOGIN_STATE_ERROR.getMsg());
        }
        String userJson = redisService.get(REDIS_KEY_PREFIX_TOKEN + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        User sysUser = JSON.parseObject(userJson, User.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    @Override
    public User findUserByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findUserByNickname(String nickname) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickname,nickname);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(User user) {
        //注意 默认生成的id 是分布式id 采用了雪花算法
        //mybatis-plus
        this.userMapper.insert(user);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null){
            user = new User();
            user.setId(1L);
            user.setAvatar("/static/img/logo.b3a48c0.png");
            user.setNickname("佚名");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(user.getAvatar());
        userVo.setNickname(user.getNickname());
        userVo.setId(String.valueOf(user.getId()));
        return userVo;
    }



    @Override
    public Integer updateLastLoginTime(Date date, Long id){
        Long times = date.getTime();
        User user = new User();
        user.setId(id);
        user.setLastLogin(times);
        return userMapper.updateById(user);
    }

}
