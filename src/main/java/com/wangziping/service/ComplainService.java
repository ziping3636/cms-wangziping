package com.wangziping.service;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Complain;
import com.wangziping.vo.ComplainVO;

public interface ComplainService {

	Boolean insert (Complain complain);

	PageInfo<Complain> selects(ComplainVO complainVO, Integer pageNum, Integer pageSize);
}
