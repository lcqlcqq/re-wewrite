package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrTag;
import com.lcq.wre.mbg.model.WrTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrTagMapper {
    int countByExample(WrTagExample example);

    int deleteByExample(WrTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrTag record);

    int insertSelective(WrTag record);

    List<WrTag> selectByExample(WrTagExample example);

    WrTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrTag record, @Param("example") WrTagExample example);

    int updateByExample(@Param("record") WrTag record, @Param("example") WrTagExample example);

    int updateByPrimaryKeySelective(WrTag record);

    int updateByPrimaryKey(WrTag record);
}