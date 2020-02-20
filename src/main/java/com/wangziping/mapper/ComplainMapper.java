package com.wangziping.mapper;

import java.util.List;

import com.wangziping.domain.Complain;
import com.wangziping.vo.ComplainVO;

public interface ComplainMapper {

	int insert (Complain complain);
	
	List<Complain> selects(ComplainVO complainVO);
}
