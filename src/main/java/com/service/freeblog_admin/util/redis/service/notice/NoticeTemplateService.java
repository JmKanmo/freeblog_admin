package com.service.freeblog_admin.util.redis.service.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeTemplateService {
    private final RedisTemplate redisTemplate;

    // TODO
}
