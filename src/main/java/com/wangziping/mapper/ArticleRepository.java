package com.wangziping.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wangziping.domain.ArticleWithBLOBs;

public interface ArticleRepository extends ElasticsearchRepository<ArticleWithBLOBs, Integer> {

}
