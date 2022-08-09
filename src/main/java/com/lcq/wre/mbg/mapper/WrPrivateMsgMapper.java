package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrPrivateMsg;
import com.lcq.wre.mbg.model.WrPrivateMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrPrivateMsgMapper {
    int countByExample(WrPrivateMsgExample example);

    int deleteByExample(WrPrivateMsgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrPrivateMsg record);

    int insertSelective(WrPrivateMsg record);

    List<WrPrivateMsg> selectByExampleWithBLOBs(WrPrivateMsgExample example);

    List<WrPrivateMsg> selectByExample(WrPrivateMsgExample example);

    WrPrivateMsg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrPrivateMsg record, @Param("example") WrPrivateMsgExample example);

    int updateByExampleWithBLOBs(@Param("record") WrPrivateMsg record, @Param("example") WrPrivateMsgExample example);

    int updateByExample(@Param("record") WrPrivateMsg record, @Param("example") WrPrivateMsgExample example);

    int updateByPrimaryKeySelective(WrPrivateMsg record);

    int updateByPrimaryKeyWithBLOBs(WrPrivateMsg record);

    int updateByPrimaryKey(WrPrivateMsg record);
}