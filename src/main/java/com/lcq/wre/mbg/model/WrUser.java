package com.lcq.wre.mbg.model;

import java.io.Serializable;

public class WrUser implements Serializable {
    private Long id;

    /**
     * 账号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 是否管理员
     *
     * @mbggenerated
     */
    private Boolean admin;

    /**
     * 头像
     *
     * @mbggenerated
     */
    private String avatar;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Long createDate;

    /**
     * 是否删除
     *
     * @mbggenerated
     */
    private Boolean deleted;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 最后登录时间
     *
     * @mbggenerated
     */
    private Long lastLogin;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobilePhoneNumber;

    /**
     * 昵称
     *
     * @mbggenerated
     */
    private String nickname;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 加密盐
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", admin=").append(admin);
        sb.append(", avatar=").append(avatar);
        sb.append(", createDate=").append(createDate);
        sb.append(", deleted=").append(deleted);
        sb.append(", email=").append(email);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", mobilePhoneNumber=").append(mobilePhoneNumber);
        sb.append(", nickname=").append(nickname);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}