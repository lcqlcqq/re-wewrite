package com.lcq.wre.mbg.model;

import java.io.Serializable;

public class WrComment implements Serializable {
    private Long id;

    /**
     * 评论内容
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 评论时间
     *
     * @mbggenerated
     */
    private Long createDate;

    /**
     * 评论所在文章id
     *
     * @mbggenerated
     */
    private Long articleId;

    /**
     * 评论者id
     *
     * @mbggenerated
     */
    private Long authorId;

    /**
     * 父评论
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     * 评论对象
     *
     * @mbggenerated
     */
    private Long toUid;

    /**
     * 评论级别（1是父评论，2是回复）
     *
     * @mbggenerated
     */
    private String level;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", createDate=").append(createDate);
        sb.append(", articleId=").append(articleId);
        sb.append(", authorId=").append(authorId);
        sb.append(", parentId=").append(parentId);
        sb.append(", toUid=").append(toUid);
        sb.append(", level=").append(level);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}