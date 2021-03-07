package com.example.postService.controllers;

import com.example.postService.services.ArticleService;
import com.example.shared.dal.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.postService.constants.PostServiceConstants.*;
import static com.example.shared.constants.FeedSystemSharedConstants.*;
import static com.example.shared.dal.model.Article.FIELD_USER_ID;

@RestController
@RequestMapping(POST_SERVICE_PATH)
public class PostServiceController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(ARTICLES_PATH)
    public ResponseEntity<String> addArticles(
            @RequestParam(TAGS) final String tags,
            @RequestParam(FILE) final MultipartFile multipartFile) throws IOException {
        articleService.addArticle(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(ARTICLES_PATH)
    public ResponseEntity<List<Article>> getArticles(
            @RequestParam(FIELD_USER_ID) final String userId,
            @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = SORT_TYPE, defaultValue = DEFAULT_SORT_TYPE) String sortType)
            throws IOException {

        List<Article> articles = articleService.getArticles(userId, page, pageSize, sortBy, sortType);
        return new ResponseEntity<>(articles, HttpStatus.OK);

    }
}
