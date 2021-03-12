package com.example.shared.response;

import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.User;
import com.example.shared.model.es.EsArticle;
import com.example.shared.utils.SharedUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponse {

    private String id;

    private User user;

    private String title;

    private byte[] content;

    private String contentType;

    private Date uploadDate;

    private String fingerprint;

    private Set<String> tags;

    private int readCount;

    private int likes;

    private int dislikes;

    public static ArticleResponse from(final Article article) throws BaseException {
        ArticleResponse articleResponse = new ArticleResponse();

        BeanUtils.copyProperties(article, articleResponse);
        articleResponse.setContent(SharedUtils.decompressBytes(article.getContent()));
        return articleResponse;
    }

    public static ArticleResponse from(final EsArticle esArticle) throws BaseException {
        ArticleResponse articleResponse = new ArticleResponse();

        BeanUtils.copyProperties(esArticle, articleResponse);
        return articleResponse;
    }

}
