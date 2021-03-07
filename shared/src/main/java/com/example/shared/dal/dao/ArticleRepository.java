package com.example.shared.dal.dao;

import com.example.shared.dal.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
    Page<Article> findByUserId(String userId, Pageable pageable);
}
