package com.example.postService.services.impl;

import com.example.postService.services.ArticleService;
import com.example.shared.dal.dao.ArticleRepository;
import com.example.shared.dal.model.Article;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    @Async
    public void addArticle(final MultipartFile file) throws IOException {
        LOGGER.info("Adding article {}", file.getName());

        Article newArticle = new Article();
        newArticle.setArticleId(UUID.randomUUID().toString());
        newArticle.setContent(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        newArticle.setUploadDate(new Date());
        newArticle.setUserId("jeyagopalc");
        articleRepository.insert(newArticle);

    }

    @Override
    public List<Article> getArticles(String userId, int page, int pageSize, String sortBy,
                                     String sortType) throws IOException {

        Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Article> pagedArticles = articleRepository.findByUserId(userId, pageable);

        return pagedArticles.getContent();
    }

}
