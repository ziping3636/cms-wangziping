package com.wangziping.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.User;
import com.wangziping.mapper.UserMapper;
import com.wangziping.service.UserService;
import com.wangziping.util.CMSException;
import com.wangziping.util.Md5Util;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public PageInfo<User> selects(String name, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> selects = userMapper.selects(name);
		return new PageInfo<User>(selects);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {

		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(User user) {
		/*
		 * if(!StringUtil.hasText(user.getNickname())) { throw new
		 * CMSException("用户名不能为空"); }
		 */

		User selectByName = userMapper.selectByName(user.getUsername());
		if (selectByName != null) {
			throw new CMSException("用户名已存在");
		}
		user.setRole("0");
		user.setCreated(new Date());
		user.setLocked(0);// 用户状态,未锁定
		user.setNickname(user.getUsername());
		user.setPassword(Md5Util.md5Encoding(user.getPassword()));
		return userMapper.insertSelective(user);
	}

	@Override
	public User login(User user) {
		User selectByName = userMapper.selectByName(user.getUsername());
		if (null == selectByName) {
			throw new CMSException("无此用户");
		} else {
			if (!selectByName.getPassword().equals(Md5Util.md5Encoding(user.getPassword()))) {
				throw new CMSException("密码不正确");
			}
		}
		return selectByName;
	}

}
