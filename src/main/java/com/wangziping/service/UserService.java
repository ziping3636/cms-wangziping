package com.wangziping.service;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.User;

public interface UserService {

	/**
	 * @Title: selects
	 * @Description: TODO模糊查询
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: PageInfo<User>
	 */
	PageInfo<User> selects(String name, Integer pageNum, Integer pageSize);

	int updateByPrimaryKeySelective(User record);

	/**
	 * @Title: insertSelective
	 * @Description: TODO注册用户
	 * @param record
	 * @return
	 * @return: int
	 */
	int insertSelective(User record);
	
	User login(User user);
}
