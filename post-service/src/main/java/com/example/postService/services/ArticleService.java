package com.example.postService.services;

import com.example.shared.dal.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    void addArticle(MultipartFile multipartFile) throws IOException;
    List<Article> getArticles(String userId, int page, int pageSize, String sortBy, String sortType)
            throws IOException;
}
