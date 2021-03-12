package com.example.shared.services.impl;

import com.example.shared.model.es.EsArticle;
import com.example.shared.services.es.EsArticleService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class EsArticleServiceImpl implements EsArticleService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public EsArticle saveOrUpdate(EsArticle esArticle) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public SearchPage<EsArticle> findByUserId(String userId, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("user.userId", userId);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<EsArticle> hits = elasticsearchOperations.search(searchQuery, EsArticle.class);
        return SearchHitSupport.searchPageFor(hits, searchQuery.getPageable());
    }

    @Override
    public void deleteByArticleId(String id) {

    }

    @Override
    public Page<EsArticle> findAll(Pageable pageable) {
        return null;
    }
}
