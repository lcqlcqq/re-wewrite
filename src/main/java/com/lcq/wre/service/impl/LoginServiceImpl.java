package com.lcq.wre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lcq.wre.dto.param.LoginEmailParam;
import com.lcq.wre.dto.param.ResetPwdParam;
import com.lcq.wre.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import com.lcq.wre.dao.mapper.UserMapper;
import com.lcq.wre.dao.pojo.User;
import com.lcq.wre.dto.ErrorCode;
import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.param.LoginParam;
import com.lcq.wre.service.LoginService;
import com.lcq.wre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    //加密盐
    private static final String salt = "quan!@#";
    @Value("${redis.key.expire.token}")
    private Long TOKEN_EXPIRE_HOURS;
    @Value("${redis.key.prefix.token}")
    private String REDIS_KEY_PREFIX_TOKEN;

    @Autowired
    private UserService sysUserService;
    @Autowired
    private UserMapper sysUserMapper;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisService redisService;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1. 检查参数合法性
         * 2. 用户名密码是否存在
         * 3. 不存在 登陆失败
         * 4. 存在 使用jwt生成token返回前端
         * 5. token放入redis中，redis 保存 token:user 的信息，设置过期时间
         *    所以登录时先去认证token合法性，去redis验证是否存在
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pwd = DigestUtils.md5Hex(password + salt);
        //查询账号和对应md5密码是否存在
        User user = sysUserService.findUser(account, pwd);
        if (user == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，更新最近一次登录时间。
        sysUserService.updateLastLoginTime(new Date(), user.getId());
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(user.getId());
//        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        redisService.set(REDIS_KEY_PREFIX_TOKEN + token, JSON.toJSONString(user), TOKEN_EXPIRE_HOURS, TimeUnit.HOURS);
        System.out.println("login.");
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisService.remove(REDIS_KEY_PREFIX_TOKEN + token);
        System.out.println("logout.");
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1. 检查参数合法性
         * 2. 检查账户是否已存在
         * 3. 注册，生成token，存入redis返回
         * 4. 事务，注册过程中出现问题回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        if (!checkEmail(loginParam.getEmail())) {
            return Result.fail(ErrorCode.EMAIL_FORMAT_ERROR.getCode(), ErrorCode.EMAIL_FORMAT_ERROR.getMsg());
        }
        User user = this.sysUserService.findUserByAccount(account);
        User NicknameUser = this.sysUserService.findUserByNickname(nickname);
        if (user != null || NicknameUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        user = new User();
        user.setNickname(nickname);
        user.setAccount(account);
        user.setPassword(DigestUtils.md5Hex(password + salt));
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        user.setAvatar("/static/user/user_default.png");
        user.setAdmin(0); //是否管理员
        user.setDeleted(0); //是否删除
        user.setSalt("");
        user.setStatus("");
        user.setEmail(loginParam.getEmail());
        this.sysUserService.save(user);

        //token生成
        String token = JWTUtils.createToken(user.getId());
        //存token
        redisService.set(REDIS_KEY_PREFIX_TOKEN + token, JSON.toJSONString(user), TOKEN_EXPIRE_HOURS, TimeUnit.DAYS);
        return Result.success(token);
    }


    @Override
    public Result resetPassWord(ResetPwdParam resetPwdParam) {
        User user = new User();
        user.setId(Long.parseLong(resetPwdParam.getId()));
        user.setPassword(DigestUtils.md5Hex(resetPwdParam.getPwd() + salt));
        int update = sysUserMapper.updateById(user);
        return update > 0 ? Result.success(update) : Result.fail(507, "重置密码失败");
    }

    @Override
    public String checkMail(String mail) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, mail);
        User user = sysUserMapper.selectOne(queryWrapper);
        if (user == null) return "";
        return user.getId().toString();
    }

    @Override
    public Result loginByEmail(LoginEmailParam loginEmailParam) {
        String email = loginEmailParam.getEmail();
        String password = loginEmailParam.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pwd = DigestUtils.md5Hex(password + salt);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        queryWrapper.eq(User::getPassword, pwd);
        User user = sysUserMapper.selectOne(queryWrapper);
        if (user == null) {
            return Result.fail(ErrorCode.EMAIL_PWD_NOT_EXIST.getCode(), ErrorCode.EMAIL_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(user.getId());
        redisService.set(REDIS_KEY_PREFIX_TOKEN + token, JSON.toJSONString(user), TOKEN_EXPIRE_HOURS, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public User checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisService.get(REDIS_KEY_PREFIX_TOKEN + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        return JSON.parseObject(userJson, User.class);
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    ////生成密钥存到数据库里的
    //public static void main(String[] args) {
    //    System.out.println(DigestUtils.md5Hex("Administrator"));
    //}

}
