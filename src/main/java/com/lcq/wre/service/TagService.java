package com.lcq.wre.service;

import com.lcq.wre.dto.Result;
import com.lcq.wre.dto.TagVo;
import com.lcq.wre.dto.param.TagParam;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);


    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    /**
     * 查询所有标签
     * @return
     */
    Result findAllDetail();

    /**
     * 标签下的文章
     * @param id
     * @return
     */
    Result findDetailById(Long id);

    /**
     *
     * 创建标签
     * @param tagParam
     * @return
     */
    Result addTag(TagParam tagParam);

    /**
     * 修改标签
     * @param tagParam
     * @return
     */
    Result modifyTag(TagParam tagParam);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Result deleteTag(String id);
}
