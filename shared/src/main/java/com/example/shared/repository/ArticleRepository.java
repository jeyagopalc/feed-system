package com.example.shared.repository;

import com.example.shared.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ArticleRepository extends MongoRepository<Article, String> {
    @Query(" { 'user.userId' : ?0 } ")
    Page<Article> findByUserId(String userId, Pageable pageable);
}
