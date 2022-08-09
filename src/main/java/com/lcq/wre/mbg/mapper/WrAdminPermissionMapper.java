package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrAdminPermission;
import com.lcq.wre.mbg.model.WrAdminPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrAdminPermissionMapper {
    int countByExample(WrAdminPermissionExample example);

    int deleteByExample(WrAdminPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrAdminPermission record);

    int insertSelective(WrAdminPermission record);

    List<WrAdminPermission> selectByExample(WrAdminPermissionExample example);

    WrAdminPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrAdminPermission record, @Param("example") WrAdminPermissionExample example);

    int updateByExample(@Param("record") WrAdminPermission record, @Param("example") WrAdminPermissionExample example);

    int updateByPrimaryKeySelective(WrAdminPermission record);

    int updateByPrimaryKey(WrAdminPermission record);
}