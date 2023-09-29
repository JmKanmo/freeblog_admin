package com.service.freeblog_admin.web.dto.music;

import com.service.freeblog_admin.web.domain.music.MusicS3;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicS3Dto {
    private final String musicUrl;
    private final String imageUrl;
    private final String uploadKey;

    public static MusicS3Dto from(MusicS3 musicS3) {
        return MusicS3Dto.builder()
                .musicUrl(musicS3.getMusicUrl())
                .imageUrl(musicS3.getImageUrl())
                .uploadKey(musicS3.getUploadKey())
                .build();
    }
}
