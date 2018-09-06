//package com.cnpc.es.service;
//
//import com.beyondli.search.MyEsRepository;
//import com.beyondli.search.entity.MyEsEntity;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by beyondLi
// * Date 2017/11/21
// * Desc .
// */
//@Service
//public class EsTestServiceImpl implements EsTestService {
//    @Resource
//    MyEsRepository myEsRepository;
//
//    /**
//     * 新增
//     * @param myEsEntity
//     */
//    @Override
//    public void add(MyEsEntity myEsEntity) {
//        myEsRepository.save(myEsEntity);
//    }
//}