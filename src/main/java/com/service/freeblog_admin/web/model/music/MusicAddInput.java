package com.service.freeblog_admin.web.model.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicAddInput {
    private final String name;
    private final String artist;
    private final String url;
    private final String cover;
    private final String lrc;
    private final Long categoryId;
}
