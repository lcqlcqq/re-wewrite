package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrArticleTag;
import com.lcq.wre.mbg.model.WrArticleTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrArticleTagMapper {
    int countByExample(WrArticleTagExample example);

    int deleteByExample(WrArticleTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrArticleTag record);

    int insertSelective(WrArticleTag record);

    List<WrArticleTag> selectByExample(WrArticleTagExample example);

    WrArticleTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrArticleTag record, @Param("example") WrArticleTagExample example);

    int updateByExample(@Param("record") WrArticleTag record, @Param("example") WrArticleTagExample example);

    int updateByPrimaryKeySelective(WrArticleTag record);

    int updateByPrimaryKey(WrArticleTag record);
}