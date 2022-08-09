package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrUser;
import com.lcq.wre.mbg.model.WrUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrUserMapper {
    int countByExample(WrUserExample example);

    int deleteByExample(WrUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrUser record);

    int insertSelective(WrUser record);

    List<WrUser> selectByExample(WrUserExample example);

    WrUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrUser record, @Param("example") WrUserExample example);

    int updateByExample(@Param("record") WrUser record, @Param("example") WrUserExample example);

    int updateByPrimaryKeySelective(WrUser record);

    int updateByPrimaryKey(WrUser record);
}