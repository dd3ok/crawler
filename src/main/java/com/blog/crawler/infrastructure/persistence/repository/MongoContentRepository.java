package com.blog.crawler.infrastructure.persistence.repository;

import com.blog.crawler.domain.crawler.Site;
import com.blog.crawler.infrastructure.persistence.entity.ContentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoContentRepository extends MongoRepository<ContentEntity, String> {
    // findLatestContents -> findAllByOrderByCreatedAtDesc
    @Query(sort = "{ createdAt: -1 }")
    List<ContentEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // findLatestContentsBySite -> findBySiteOrderByCreatedAtDesc
    @Query(value = "{ 'site': ?0 }", sort = "{ createdAt: -1 }")
    List<ContentEntity> findBySiteOrderByCreatedAtDesc(Site site, Pageable pageable);

    // 네이트 뉴스 중복 체크를 위한 메소드 추가
    @Query(value = "{ 'site': ?0, 'title': ?1 }", exists = true)
    boolean existsBySiteAndTitle(Site site, String title);

    // 특정 사이트의 최신 컨텐츠 조회 (생성 시간 기준)
    @Query(value = "{ 'site': ?0 }", sort = "{ createdAt: -1 }")
    List<ContentEntity> findLatestBySite(Site site, Pageable pageable);
}