package com.wangziping.mapper;

import java.util.List;

import com.wangziping.domain.Collect;

public interface CollectMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

	List<Collect> selectsByUserId(Integer userId);
}