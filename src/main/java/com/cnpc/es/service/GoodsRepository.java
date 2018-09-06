package com.cnpc.es.service;

import com.cnpc.es.entity.UserVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dalaoyang
 * @Description
 * @project springboot_learn
 * @package com.dalaoyang.repository
 * @email yangyang@dalaoyang.cn
 * @date 2018/5/4
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<UserVo,Long> {
}