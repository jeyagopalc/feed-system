package com.example.shared.repository.es;

import com.example.shared.model.es.EsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by jeyagopal on 2017/8/20.
 */
public interface EsArticleRepository extends ElasticsearchRepository<EsArticle,String> {

    @Query(" { 'user.userId' : ?0 } ")
    Page<EsArticle> findByUserId(final String userId, final Pageable pageable);

    @Query(" { 'user.userId' : ?0 } ")
    List<EsArticle> findByUserId(final String userId);

    void deleteEsArticleById(String id);
}
