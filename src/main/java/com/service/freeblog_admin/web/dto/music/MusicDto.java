package com.service.freeblog_admin.web.dto.music;

import com.service.freeblog_admin.web.domain.music.Music;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicDto {
    private Long id;

    private Long categoryId;

    private String categoryName;

    private String name;

    private String artist;

    private String url;

    private String cover;

    private String lrc;

    public static MusicDto from(Music music) {
        return MusicDto.builder()
                .id(music.getId())
                .categoryId(music.getMusicCategory().getId())
                .categoryName(music.getMusicCategory().getName())
                .name(music.getName())
                .artist(music.getArtist())
                .url(music.getUrl())
                .cover(music.getCover())
                .lrc(music.getLrc())
                .build();
    }
}
