package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrAdmin;
import com.lcq.wre.mbg.model.WrAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrAdminMapper {
    int countByExample(WrAdminExample example);

    int deleteByExample(WrAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrAdmin record);

    int insertSelective(WrAdmin record);

    List<WrAdmin> selectByExample(WrAdminExample example);

    WrAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrAdmin record, @Param("example") WrAdminExample example);

    int updateByExample(@Param("record") WrAdmin record, @Param("example") WrAdminExample example);

    int updateByPrimaryKeySelective(WrAdmin record);

    int updateByPrimaryKey(WrAdmin record);
}