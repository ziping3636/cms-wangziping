package com.wangziping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangziping.domain.User;

public interface UserMapper {
	
	List<User> selects(@Param("name")String name);
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByName(@Param("username")String username);
}