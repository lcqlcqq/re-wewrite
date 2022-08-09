package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrPermission;
import com.lcq.wre.mbg.model.WrPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrPermissionMapper {
    int countByExample(WrPermissionExample example);

    int deleteByExample(WrPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrPermission record);

    int insertSelective(WrPermission record);

    List<WrPermission> selectByExample(WrPermissionExample example);

    WrPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrPermission record, @Param("example") WrPermissionExample example);

    int updateByExample(@Param("record") WrPermission record, @Param("example") WrPermissionExample example);

    int updateByPrimaryKeySelective(WrPermission record);

    int updateByPrimaryKey(WrPermission record);
}