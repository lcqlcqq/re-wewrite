package com.lcq.wre.mbg.mapper;

import com.lcq.wre.mbg.model.WrFavorites;
import com.lcq.wre.mbg.model.WrFavoritesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WrFavoritesMapper {
    int countByExample(WrFavoritesExample example);

    int deleteByExample(WrFavoritesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WrFavorites record);

    int insertSelective(WrFavorites record);

    List<WrFavorites> selectByExample(WrFavoritesExample example);

    WrFavorites selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WrFavorites record, @Param("example") WrFavoritesExample example);

    int updateByExample(@Param("record") WrFavorites record, @Param("example") WrFavoritesExample example);

    int updateByPrimaryKeySelective(WrFavorites record);

    int updateByPrimaryKey(WrFavorites record);
}