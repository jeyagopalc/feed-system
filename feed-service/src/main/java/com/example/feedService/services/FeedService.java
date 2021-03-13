package com.example.feedService.services;

import com.example.shared.exception.BaseException;
import com.example.shared.request.GenericGetRequest;
import com.example.shared.response.ArticleResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface FeedService {
    List<ArticleResponse> findArticleByUserId(GenericGetRequest genericGetRequest)
            throws BaseException;
}
