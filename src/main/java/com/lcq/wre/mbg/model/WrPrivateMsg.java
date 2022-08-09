package com.lcq.wre.mbg.model;

import java.io.Serializable;

public class WrPrivateMsg implements Serializable {
    private Long id;

    /**
     * 发送者
     *
     * @mbggenerated
     */
    private Long fromId;

    /**
     * 接收者
     *
     * @mbggenerated
     */
    private Long toId;

    /**
     * 发送时间
     *
     * @mbggenerated
     */
    private Long time;

    /**
     * 是否删除
     *
     * @mbggenerated
     */
    private Integer delete;

    /**
     * 消息内容
     *
     * @mbggenerated
     */
    private String body;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromId=").append(fromId);
        sb.append(", toId=").append(toId);
        sb.append(", time=").append(time);
        sb.append(", delete=").append(delete);
        sb.append(", body=").append(body);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}