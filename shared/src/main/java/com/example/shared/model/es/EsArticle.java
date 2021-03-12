package com.example.shared.model.es;

import com.example.shared.model.Article;
import com.example.shared.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.Date;
import java.util.Set;

@Document(indexName = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsArticle {
    @Id
    private String id;

    private User user;

    private String title;

    private byte[] partialContent;

    private String contentType;

    @Field(type = FieldType.Date, store = true,
            format = DateFormat.date_hour_minute_second_millis)
    private Date uploadDate;

    private String fingerprint;

    private Set<String> tags;

    private int readCount;

    private int likes;

    private int dislikes;

    public EsArticle(Article article){
        this.id = article.getId();
        this.user = article.getUser();
        this.title = article.getTitle();
        this.contentType = article.getContentType();
        this.uploadDate = article.getUploadDate();
        this.tags = article.getTags();
        this.readCount = article.getReadCount();
        this.likes = article.getLikes();
        this.dislikes = article.getDislikes();
        this.fingerprint = article.getFingerprint();
    }
}
