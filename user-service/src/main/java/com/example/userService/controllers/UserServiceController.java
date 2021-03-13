package com.example.userService.controllers;

import com.example.shared.error.ErrorTracker;
import com.example.shared.model.User;
import com.example.shared.utils.ServerResponseManager;
import com.example.userService.services.UsersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.shared.error.ErrorCodes.*;
import static com.example.userService.constants.UserServiceConstants.*;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserServiceController {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "Create new user",
            notes = "Create new user and persist into database",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request on creating user"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User userDetails) {

        ErrorTracker errorTracker = new ErrorTracker();

        if (!userDetails.validate(errorTracker)) {
            return ServerResponseManager.constructBadRequestResponse(errorTracker);
        }

        try {
            User returnValue = usersService.createUser(userDetails);
            return ServerResponseManager.constructCreateSuccessResponse(returnValue);

        } catch(Exception e) {
            logger.error(FEED_011.getDescription(), e);
            return ServerResponseManager.constructInternalServerErrorResponse(FEED_011, userDetails);
        }
    }

    @ApiOperation(value = "Get user",
            notes = "Get user details",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request on creating user"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping("/{" + USER_ID + "}")
    public ResponseEntity<Object> getUser(@PathVariable(USER_ID) String userId) {

        if (StringUtils.isBlank(userId)) {
            ErrorTracker errorTracker = new ErrorTracker();
            errorTracker.addError(FEED_002, USER_ID);
            return ServerResponseManager.constructBadRequestResponse(errorTracker);
        }

        try {
            User returnValue = usersService.getUserByUserId(userId);
            return ServerResponseManager.constructCreateSuccessResponse(returnValue);

        } catch(Exception e) {
            logger.error(FEED_010.getDescription(), e);
            return ServerResponseManager.constructInternalServerErrorResponse(FEED_010, userId);
        }
    }
}
