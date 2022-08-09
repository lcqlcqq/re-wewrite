package com.lcq.wre.mbg.model;

import java.io.Serializable;

public class WrArticle implements Serializable {
    private Long id;

    /**
     * 评论数量
     *
     * @mbggenerated
     */
    private Integer commentCounts;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Long createDate;

    /**
     * 简介
     *
     * @mbggenerated
     */
    private String summary;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 浏览数量
     *
     * @mbggenerated
     */
    private Integer viewCounts;

    /**
     * 是否置顶
     *
     * @mbggenerated
     */
    private Integer weight;

    /**
     * 作者id
     *
     * @mbggenerated
     */
    private Long authorId;

    /**
     * 内容id
     *
     * @mbggenerated
     */
    private Long bodyId;

    /**
     * 类别id
     *
     * @mbggenerated
     */
    private Integer categoryId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(Integer commentCounts) {
        this.commentCounts = commentCounts;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(Integer viewCounts) {
        this.viewCounts = viewCounts;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBodyId() {
        return bodyId;
    }

    public void setBodyId(Long bodyId) {
        this.bodyId = bodyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentCounts=").append(commentCounts);
        sb.append(", createDate=").append(createDate);
        sb.append(", summary=").append(summary);
        sb.append(", title=").append(title);
        sb.append(", viewCounts=").append(viewCounts);
        sb.append(", weight=").append(weight);
        sb.append(", authorId=").append(authorId);
        sb.append(", bodyId=").append(bodyId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}