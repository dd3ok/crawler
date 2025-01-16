package com.blog.crawler.domain.crawler;

import com.blog.crawler.infrastructure.persistence.entity.ContentEntity;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper {
    public ContentEntity toEntity(Content content) {
        return ContentEntity.builder()
                .id(content.getId())
                .site(content.getSite())
                .boardId(content.getBoardId())
                .title(content.getTitle())
                .content(content.getContent())
                .author(content.getAuthor())
                .url(content.getUrl())
                .postDate(content.getPostDate())
                .viewCount(content.getViewCount())
                .commentCount(content.getCommentCount())
                .likeCount(content.getLikeCount())
                .build();
    }

    public Content toDomain(ContentEntity entity) {
        return Content.builder()
                .id(entity.getId())
                .site(entity.getSite())
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .url(entity.getUrl())
                .postDate(entity.getPostDate())
                .viewCount(entity.getViewCount())
                .commentCount(entity.getCommentCount())
                .likeCount(entity.getLikeCount())
                .build();
    }
}
