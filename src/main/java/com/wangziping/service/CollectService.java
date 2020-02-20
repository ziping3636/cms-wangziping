package com.wangziping.service;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Collect;

public interface CollectService {

	int doAddCollect(Collect collect);

	PageInfo<Collect> selects(Integer userId, int pageNum, int pageSize);

	void deleteCollect(Integer id);

}
