package com.example.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "articles")
public class Article {
    @Id
    private String id;

    private User user;

    private String title;

    private byte[] content;

    private String contentType;

    private Date uploadDate;

    private String fingerprint;

    private Set<String> tags;

    private int readCount;

    private int likes;

    private int dislikes;

}
