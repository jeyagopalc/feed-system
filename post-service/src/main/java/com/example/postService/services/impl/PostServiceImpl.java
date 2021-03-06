package com.example.postService.services.impl;

import com.example.postService.services.PostService;
import com.example.shared.exception.BaseException;
import com.example.shared.model.Article;
import com.example.shared.model.User;
import com.example.shared.model.es.EsArticle;
import com.example.shared.repository.ArticleRepository;
import com.example.shared.repository.es.EsArticleRepository;
import com.example.shared.services.es.EsArticleService;
import com.example.shared.utils.SharedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.Date;

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

    @Transactional
    @Override
    public void deleteArticle(String id) {

    }

}
