package com.wangziping.mapper;

import com.wangziping.domain.Settings;

public interface SettingsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Settings record);

    int insertSelective(Settings record);

    Settings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);
}