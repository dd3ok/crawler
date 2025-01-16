package com.blog.crawler.infrastructure.persistence.entity;

import com.blog.crawler.domain.common.BaseEntity;
import com.blog.crawler.domain.crawler.Site;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "contents")
public class ContentEntity extends BaseEntity {
    @Id
    private String id;
    private Site site;
    private String boardId;
    private String title;
    private String content;
    private String author;
    private String url;
    private LocalDateTime postDate;
    private int viewCount;
    private int commentCount;
    private int likeCount;

    @Builder
    public ContentEntity(String id, Site site, String boardId, String title, 
                        String content, String author, String url, 
                        LocalDateTime postDate, int viewCount, 
                        int commentCount, int likeCount) {
        this.id = id;
        this.site = site;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.url = url;
        this.postDate = postDate;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
}