package com.example.shared.services;

import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.User;
import com.example.shared.request.GenericGetRequest;
import com.example.shared.response.ArticleResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Article saveOrUpdateArticle(User user, String title, String tags, MultipartFile multipartFile) throws BaseException;
    List<ArticleResponse> findArticleByUserId(GenericGetRequest genericGetRequest)
            throws BaseException;
    void deleteArticle(String id);
}
