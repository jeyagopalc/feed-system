package com.example.userService.controllers;

import com.example.shared.model.User;
import com.example.userService.services.UsersService;
import com.example.userService.utils.UserTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceControllerTest {
    @Mock
    private UsersService usersService;

    @InjectMocks
    private UserServiceController userServiceController;

    @Test
    void testCreateUser() {
        User userRequest = UserTestUtils.createUser();
        Mockito.when(usersService.createUser(Mockito.any())).thenReturn(userRequest);


        ResponseEntity<Object> responseEntity = userServiceController.createUser(userRequest);
        User response = (User) responseEntity.getBody();

        assertEquals(response.getFirstName(), userRequest.getFirstName());

        Mockito.verify(usersService).createUser(Mockito.any());

    }

    @Test
    void testCreateUserWithBadRequestResponse() {
        User userRequest = UserTestUtils.createUser();
        userRequest.setEmail(null);
        ResponseEntity<Object> responseEntity = userServiceController.createUser(userRequest);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateUserWithInternalErrorResponse() {
        User userDetails = UserTestUtils.createUser();
        Mockito.doThrow(UsernameNotFoundException.class)
                .when(usersService)
                .createUser(Mockito.any());
        ResponseEntity<Object> responseEntity = userServiceController.createUser(userDetails);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetUser() {
        User userDetails = UserTestUtils.createUser();
        Mockito.when(usersService.getUserByUserId(Mockito.anyString())).thenReturn(userDetails);

        ResponseEntity<Object> responseEntity = userServiceController.getUser(userDetails.getUserId());
        User response = (User) responseEntity.getBody();

        assertEquals(response.getUserId(), userDetails.getUserId());

        Mockito.verify(usersService).getUserByUserId(Mockito.anyString());

    }

    @Test
    void testGetUserWithBadRequestResponse() {
        ResponseEntity<Object> responseEntity = userServiceController.getUser(null);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetUserWithInternalServerErrorResponse() {
        User userDetails = UserTestUtils.createUser();
        Mockito.doThrow(UsernameNotFoundException.class)
                .when(usersService)
                .getUserByUserId(Mockito.anyString());
        ResponseEntity<Object> responseEntity = userServiceController.getUser(userDetails.getUserId());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}