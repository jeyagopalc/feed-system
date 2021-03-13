package com.example.postService.services;

import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {
    Article saveOrUpdateArticle(User user, String title, String tags, MultipartFile multipartFile) throws BaseException;
    void deleteArticle(String id);
}
