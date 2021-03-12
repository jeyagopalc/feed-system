package com.example.postService.services.impl;

import com.example.postService.services.PostService;
import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.User;
import com.example.shared.model.es.EsArticle;
import com.example.shared.repository.ArticleRepository;
import com.example.shared.repository.es.EsArticleRepository;
import com.example.shared.request.GenericGetRequest;
import com.example.shared.response.ArticleResponse;
import com.example.shared.services.es.EsArticleService;
import com.example.shared.utils.FeedType;
import com.example.shared.utils.SharedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.shared.error.ErrorCodes.FEED_005;


@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EsArticleRepository esArticleRepository;

    @Autowired
    private EsArticleService esArticleService;

    @Override
    @Async
    @Transactional
    public Article saveOrUpdateArticle(final User user, final String title, final String tags,
                                       final MultipartFile multipartFile) throws BaseException {

        Article article = new Article();
        article.setTitle(title);
        article.setTags(SharedUtils.parseTags(tags));
        article.setUploadDate(new Date());
        article.setContentType(multipartFile.getContentType());
        article.setUser(user);

        Article returnValue;
        try {
            byte[] bytes = multipartFile.getBytes();
            article.setContent(SharedUtils.compressBytes(bytes));
            article.setFingerprint(SharedUtils.getFingerPrintMessage(multipartFile.getInputStream()));

            returnValue = articleRepository.save(article);

            EsArticle esArticle = new EsArticle(article);
            esArticle.setPartialContent(new ByteArrayInputStream(bytes).readNBytes(512));
            esArticleRepository.save(esArticle);

        } catch(Exception e) {
            logger.error(FEED_005.getDescription());
            throw new BaseException(FEED_005, e);
        }
        return returnValue;
    }

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

    @Transactional
    @Override
    public void deleteArticle(String id) {

    }

}
