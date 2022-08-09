package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrArticle;
import com.lcq.wre.mbg.model.WrArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrArticleMapper {
    int countByExample(WrArticleExample example);

    int deleteByExample(WrArticleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrArticle record);

    int insertSelective(WrArticle record);

    List<WrArticle> selectByExample(WrArticleExample example);

    WrArticle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrArticle record, @Param("example") WrArticleExample example);

    int updateByExample(@Param("record") WrArticle record, @Param("example") WrArticleExample example);

    int updateByPrimaryKeySelective(WrArticle record);

    int updateByPrimaryKey(WrArticle record);
}