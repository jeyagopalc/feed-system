package com.example.shared.model;

import com.example.shared.error.ErrorTracker;
import com.example.shared.utils.FieldValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    public User(String userId) {
        this.userId = userId;
    }

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Indexed(unique=true)
    private String email;

    @Indexed(unique=true)
    private String userId;

    @JsonIgnore
    private String password;

    private String encryptedPassword;

    public boolean validate(final ErrorTracker errorTracker) {
        FieldValidator.validateNonEmptyString("email", email, errorTracker);
        FieldValidator.validateNonEmptyString("password", password, errorTracker);

        return errorTracker.isSuccess();
    }

}
