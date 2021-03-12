package com.example.shared.services.es;

import com.example.shared.model.es.EsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;


public interface EsArticleService {
    EsArticle saveOrUpdate(EsArticle esArticle);

    void delete(String id);

    SearchPage<EsArticle> findByUserId(String userId, Pageable pageable);

    void deleteByArticleId(String id);

    Page<EsArticle> findAll(Pageable pageable);
}
