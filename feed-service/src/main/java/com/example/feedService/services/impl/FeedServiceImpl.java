package com.example.feedService.services.impl;

import com.example.feedService.services.FeedService;
import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.es.EsArticle;
import com.example.shared.repository.ArticleRepository;
import com.example.shared.repository.es.EsArticleRepository;
import com.example.shared.request.GenericGetRequest;
import com.example.shared.response.ArticleResponse;
import com.example.shared.services.es.EsArticleService;
import com.example.shared.utils.FeedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class FeedServiceImpl implements FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EsArticleRepository esArticleRepository;

    @Autowired
    private EsArticleService esArticleService;

    @Override
    public List<ArticleResponse> findArticleByUserId(GenericGetRequest genericGetRequest)
            throws BaseException {

        String userId = genericGetRequest.getUser().getUserId();

        List<ArticleResponse> articleResponses = new ArrayList<>();
        if ( genericGetRequest.getFeedType() == FeedType.FULL) {
            Page<Article> pagedArticles;
            pagedArticles = articleRepository.findByUserId(userId, genericGetRequest.getPageable());
            for (Article article : pagedArticles.getContent()) {
                articleResponses.add(ArticleResponse.from(article));
            }
        } else {
            SearchPage<EsArticle> pagedArticles;
            pagedArticles = esArticleService.findByUserId(userId, genericGetRequest.getPageable());

            for (SearchHit<EsArticle> article : pagedArticles.getContent()) {
                articleResponses.add(ArticleResponse.from(article.getContent()));
            }
        }

        return articleResponses;
    }
}
