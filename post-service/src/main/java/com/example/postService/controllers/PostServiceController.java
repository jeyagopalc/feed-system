package com.example.postService.controllers;

import com.example.postService.services.PostService;
import com.example.shared.exception.BaseException;
import com.example.shared.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.example.postService.constants.PostServiceConstants.*;

@RestController
@RequestMapping(POST_SERVICE_PATH)
public class PostServiceController {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceController.class);

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Add article",
        notes = "Add article for a specific user",
        response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request on adding articles"),
            @ApiResponse(code = 401, message = "Unauthorized user"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping(ARTICLES_PATH)
    public ResponseEntity<String> addArticle(@RequestParam(TITLE)final String title,
                                             @RequestParam(TAGS) final String tags,
                                             @RequestParam(FILE) final MultipartFile multipartFile)
            throws BaseException {

        postService.saveOrUpdateArticle(new User("jeyagopalc"), title, tags, multipartFile);

        final String response = String.format("[%s] uploaded successfully", multipartFile.getOriginalFilename());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
