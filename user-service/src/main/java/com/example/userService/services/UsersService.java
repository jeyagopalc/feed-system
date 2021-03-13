package com.example.userService.services;

import com.example.shared.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
	User createUser(User userDetails);
	User getUserDetailsByEmail(String email);
	User getUserByUserId(String userId);
}
