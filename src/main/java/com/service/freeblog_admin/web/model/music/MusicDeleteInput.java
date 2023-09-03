package com.service.freeblog_admin.web.model.music;

import com.service.freeblog_admin.web.dto.music.MusicDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicDeleteInput {
    private final Long deleteId;

    private final Long deleteCategoryId;

    public static MusicDeleteInput from(MusicDto musicDto) {
        return MusicDeleteInput.builder()
                .deleteId(musicDto.getId())
                .deleteCategoryId(musicDto.getCategoryId())
                .build();
    }
}
