package com.example.shared.dal.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Document(collection = "articles")
public class Article {
    public static final String FIELD_ARTICLE_ID = "articleId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_UPLOAD_DATE = "uploadDate";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_FINGERPRINT = "fingerprint";
    public static final String FIELD_TAGS = "tags";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_DISLIKES = "dislikes";

    @Id
    private String id;

    @Indexed
    @Field(FIELD_USER_ID)
    private String userId;

    @Indexed(unique = true)
    @Field(FIELD_ARTICLE_ID)
    private String articleId;

    @Field(FIELD_TITLE)
    private String title;

    @Field(FIELD_CONTENT)
    private Binary content;

    @Field(FIELD_UPLOAD_DATE)
    private Date uploadDate;

    @Field(FIELD_FINGERPRINT)
    private String fingerprint;

    @Field(FIELD_TAGS)
    private Set<String> tags;

    @Field(FIELD_LIKES)
    private int likes;

    @Field(FIELD_DISLIKES)
    private int dislikes;

}
