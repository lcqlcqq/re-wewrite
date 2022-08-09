package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrArticleBody;
import com.lcq.wre.mbg.model.WrArticleBodyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrArticleBodyMapper {
    int countByExample(WrArticleBodyExample example);

    int deleteByExample(WrArticleBodyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrArticleBody record);

    int insertSelective(WrArticleBody record);

    List<WrArticleBody> selectByExampleWithBLOBs(WrArticleBodyExample example);

    List<WrArticleBody> selectByExample(WrArticleBodyExample example);

    WrArticleBody selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrArticleBody record, @Param("example") WrArticleBodyExample example);

    int updateByExampleWithBLOBs(@Param("record") WrArticleBody record, @Param("example") WrArticleBodyExample example);

    int updateByExample(@Param("record") WrArticleBody record, @Param("example") WrArticleBodyExample example);

    int updateByPrimaryKeySelective(WrArticleBody record);

    int updateByPrimaryKeyWithBLOBs(WrArticleBody record);

    int updateByPrimaryKey(WrArticleBody record);
}