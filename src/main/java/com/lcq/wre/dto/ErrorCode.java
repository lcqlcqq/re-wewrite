package com.lcq.wre.dto;

public enum  ErrorCode {
    LOGIN_STATE_ERROR(1100,"未登录"),
    SESSION_TIME_OUT(1101,"会话超时"),

    ACCOUNT_EXIST(1201,"用户名或昵称已存在"),
    ACCOUNT_PWD_NOT_EXIST(1202,"用户名不存在或密码错误"),
    EMAIL_PWD_NOT_EXIST(1203,"邮箱账号不存在或密码错误"),
    EMAIL_FORMAT_ERROR(1204,"邮箱格式错误"),
    EMAIL_ALREADY_USED(1205,"该邮箱已被注册"),
    EMAIL_NOT_EXIST(1206,"该邮箱不存在，请检查填写的邮箱"),

    PARAMS_ERROR(1301,"参数有误"),
    NO_PERMISSION(1302,"无访问权限"),
    TOKEN_ERROR(1303,"token不合法"),

    CATEGORY_NAME_DUPLICATED(1900,"类别名称重复!"),
    CATEGORY_NOT_EXIST(1901,"类别不存在!"),
    TAG_NAME_DUPLICATED(1902,"标签名称重复!"),
    TAG_NOT_EXIST(1903,"标签不存在!");

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
