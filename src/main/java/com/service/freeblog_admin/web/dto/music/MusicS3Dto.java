package com.service.freeblog_admin.web.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicS3Dto {
    private final String musicUrl;
    private final String imageUrl;
    private final String uploadKey;
}
