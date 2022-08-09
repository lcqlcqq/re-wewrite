package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrCategory;
import com.lcq.wre.mbg.model.WrCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrCategoryMapper {
    int countByExample(WrCategoryExample example);

    int deleteByExample(WrCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrCategory record);

    int insertSelective(WrCategory record);

    List<WrCategory> selectByExample(WrCategoryExample example);

    WrCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrCategory record, @Param("example") WrCategoryExample example);

    int updateByExample(@Param("record") WrCategory record, @Param("example") WrCategoryExample example);

    int updateByPrimaryKeySelective(WrCategory record);

    int updateByPrimaryKey(WrCategory record);
}