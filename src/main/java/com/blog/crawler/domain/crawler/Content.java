package com.blog.crawler.domain.crawler;

import com.blog.crawler.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Content extends BaseEntity {
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
}
