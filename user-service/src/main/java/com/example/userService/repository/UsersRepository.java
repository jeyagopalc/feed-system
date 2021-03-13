package com.example.userService.repository;

import com.example.shared.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByUserId(String userId);
}
