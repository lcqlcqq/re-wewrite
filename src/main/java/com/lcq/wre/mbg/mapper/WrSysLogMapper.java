package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrSysLog;
import com.lcq.wre.mbg.model.WrSysLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrSysLogMapper {
    int countByExample(WrSysLogExample example);

    int deleteByExample(WrSysLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrSysLog record);

    int insertSelective(WrSysLog record);

    List<WrSysLog> selectByExample(WrSysLogExample example);

    WrSysLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrSysLog record, @Param("example") WrSysLogExample example);

    int updateByExample(@Param("record") WrSysLog record, @Param("example") WrSysLogExample example);

    int updateByPrimaryKeySelective(WrSysLog record);

    int updateByPrimaryKey(WrSysLog record);
}