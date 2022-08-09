package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrComment;
import com.lcq.wre.mbg.model.WrCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrCommentMapper {
    int countByExample(WrCommentExample example);

    int deleteByExample(WrCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrComment record);

    int insertSelective(WrComment record);

    List<WrComment> selectByExample(WrCommentExample example);

    WrComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrComment record, @Param("example") WrCommentExample example);

    int updateByExample(@Param("record") WrComment record, @Param("example") WrCommentExample example);

    int updateByPrimaryKeySelective(WrComment record);

    int updateByPrimaryKey(WrComment record);
}