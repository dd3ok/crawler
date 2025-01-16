package com.blog.crawler.infrastructure.persistence;

import com.blog.crawler.application.port.out.ContentPersistencePort;
import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.infrastructure.persistence.entity.ContentEntity;
import com.blog.crawler.infrastructure.persistence.repository.MongoContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentPersistenceAdapter implements ContentPersistencePort {
    private final MongoContentRepository repository;

    @Override
    public void save(Content content) {
        ContentEntity entity = ContentEntity.builder()
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
            
        repository.save(entity);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
