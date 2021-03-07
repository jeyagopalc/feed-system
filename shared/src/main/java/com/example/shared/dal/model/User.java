package com.example.shared.dal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "users")
public class User {

    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_ENCRYPTED_PASSWORD = "encryptedPassword";

    @Id
    private String id;

    @Field(FIELD_FIRST_NAME)
    private String firstName;

    @Field(FIELD_LAST_NAME)
    private String lastName;

    @Field(FIELD_EMAIL)
    private String email;

    @Indexed(unique = true)
    @Field(FIELD_USER_ID)
    private String userId;

    @Field(FIELD_ENCRYPTED_PASSWORD)
    private String encryptedPassword;

}
