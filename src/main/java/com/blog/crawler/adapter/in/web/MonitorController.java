package com.blog.crawler.adapter.in.web;

import com.blog.crawler.domain.crawler.Content;
import com.blog.crawler.domain.crawler.ContentMapper;
import com.blog.crawler.domain.crawler.Site;
import com.blog.crawler.infrastructure.persistence.entity.ContentEntity;
import com.blog.crawler.infrastructure.persistence.repository.MongoContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/monitor")
@RequiredArgsConstructor
public class MonitorController {
    private final MongoContentRepository contentRepository;
    private final ContentMapper contentMapper;  // 추가

    @GetMapping("/contents")
    public ResponseEntity<List<Content>> getLatestContents(
            @RequestParam(required = false) Site site,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        List<ContentEntity> contents = site == null ?
                contentRepository.findAllByOrderByCreatedAtDesc(pageRequest) :
                contentRepository.findBySiteOrderByCreatedAtDesc(site, pageRequest);

        return ResponseEntity.ok(contents.stream()
                .map(contentMapper::toDomain)
                .collect(Collectors.toList()));
    }
}
