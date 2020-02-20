package com.wangziping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangziping.domain.Category;

public interface CategoryMapper {
	
	List<Category> selectsByChannelId(@Param("channelId")Integer channelId);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}