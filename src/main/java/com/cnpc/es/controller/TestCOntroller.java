package com.cnpc.es.controller;

import com.cnpc.common.controller.BaseController;
import com.cnpc.common.response.ResponseMessage;
import com.cnpc.common.util.Query;
import com.cnpc.es.entity.UserVo;
import com.cnpc.es.service.GoodsRepository;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestCOntroller {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/getGoodsList")
    public List<UserVo> getList(Integer pageNumber, String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=getEntitySearchQuery(pageNumber,10,query);
        Page<UserVo> goodsPage = (Page<UserVo>) goodsRepository.findAll();
        return goodsPage.getContent();
    }


    @PostMapping("/all/{pinyin}")
    public List<Map<String, Object>> searchAll(@PathVariable("pinyin") String pinyin) throws Exception {
        //这一步是最关键的
        Client client = elasticsearchTemplate.getClient();
        // @Document(indexName = "product", type = "book")
        SearchRequestBuilder srb = client.prepareSearch("myindex").setTypes("test");
        // QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        SearchResponse sr;
        if (pinyin.equals("null")) {
            sr = srb
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setSize(20)
                    .execute().actionGet();
        } else {
            sr = srb
                    .setQuery(QueryBuilders.wildcardQuery("pinyin", ("*"+pinyin+"*").toLowerCase()))
                    .setSize(20)
                    .execute().actionGet();
        }
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
        return list;
    }

    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("name", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(QueryBuilders.matchPhraseQuery("description", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                //设置权重分 求和模式
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);

        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }
}
