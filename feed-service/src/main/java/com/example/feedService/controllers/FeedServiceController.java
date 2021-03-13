package com.example.feedService.controllers;

import com.example.feedService.services.FeedService;
import com.example.shared.error.ErrorTracker;
import com.example.shared.exception.BaseException;
import com.example.shared.model.User;
import com.example.shared.request.GenericGetRequest;
import com.example.shared.response.ArticleResponse;
import com.example.shared.utils.ServerResponseManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.feedService.constant.FeedServiceConstant.ARTICLES_PATH;
import static com.example.feedService.constant.FeedServiceConstant.FEED_SERVICE_PATH;
import static com.example.shared.constants.SharedConstants.*;
import static com.example.shared.error.ErrorCodes.FEED_004;
import static com.example.shared.error.ErrorCodes.FEED_008;

@RestController
@RequestMapping(FEED_SERVICE_PATH)
public class FeedServiceController {

    private static final Logger logger = LoggerFactory.getLogger(FeedServiceController.class);

    @Autowired
    private FeedService feedService;

    @ApiOperation(value = "Get articles",
            notes = "Get articles for a specific user",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request on getting articles"),
            @ApiResponse(code = 401, message = "Unauthorized user"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping(ARTICLES_PATH)
    public ResponseEntity<Object> getArticles(
            @RequestParam(value = FEED_TYPE, required = false) final String feedType,
            @RequestParam(value = QUERY, required = false) final String query,
            @RequestParam(value = FIELDS, required = false) final String fields,
            @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = DEFAULT_SORT_BY) final String sortBy,
            @RequestParam(value = SORT_TYPE, defaultValue = DEFAULT_SORT_TYPE) final String sortType)
            throws BaseException {

        GenericGetRequest genericGetRequest =
                new GenericGetRequest(new User("jeyagopalc"), feedType, fields, query, page, pageSize,
                        sortBy, sortType);

        final ErrorTracker errorTracker = new ErrorTracker();

        if (!genericGetRequest.validate(errorTracker)) {
            logger.error(FEED_004.getDescription(), genericGetRequest, errorTracker.getErrors());
            return ServerResponseManager.constructBadRequestResponse(errorTracker);
        }

        try {
            List<ArticleResponse> articleResponses = feedService.findArticleByUserId(genericGetRequest);
            return ServerResponseManager.constructSuccessResponse(articleResponses);
        } catch(Exception e) {
            logger.error(FEED_008.getDescription());
            throw new BaseException(FEED_008, e);
        }
    }
}
