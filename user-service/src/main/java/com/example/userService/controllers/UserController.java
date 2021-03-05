package com.example.userService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.userService.constants.UserConstants.USER_CONTROLLER_PATH;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {
    @GetMapping(
            produces =  {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            } )
    public ResponseEntity<String> getUser() {
        return new ResponseEntity<>("Jeyagopal", HttpStatus.OK);
    }
}
