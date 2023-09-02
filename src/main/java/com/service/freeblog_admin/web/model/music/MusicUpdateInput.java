package com.service.freeblog_admin.web.model.music;

import com.service.freeblog_admin.web.dto.music.MusicDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class MusicUpdateInput {
    private final Long updateId;

    private final Long updateCategoryId;

    private final String updateCategoryName;

    @NotEmpty(message = "뮤직명이 비어있습니다.")
    @NotBlank(message = "뮤직명은 공백만 올 수 없습니다.")
    @Size(max = 50, message = "뮤직명은 최대 50글자 까지 작성 가능합니다.")
    private final String updateName;

    @NotEmpty(message = "뮤직 아티스트명이 비어있습니다.")
    @NotBlank(message = "뮤직 아티스트명은 공백만 올 수 없습니다.")
    @Size(max = 50, message = "뮤직 아티스트명은 최대 50글자 까지 작성 가능합니다.")
    private final String updateArtist;

    @NotEmpty(message = "뮤직 URL이 비어있습니다.")
    @NotBlank(message = "뮤직 URL은 공백만 올 수 없습니다.")
    @Size(max = 300, message = "뮤직 URL은 최대 300글자 까지 작성 가능합니다.")
    private final String updateUrl;

    @NotEmpty(message = "뮤직 COVER이 비어있습니다.")
    @NotBlank(message = "뮤직 COVER은 공백만 올 수 없습니다.")
    @Size(max = 300, message = "뮤직 COVER은 최대 300글자 까지 작성 가능합니다.")
    private final String updateCover;

    @Size(max = 300, message = "뮤직 LRC은 최대 10000글자 까지 작성 가능합니다.")
    private final String updateLrc;

    public static MusicUpdateInput from(MusicDto musicDto) {
        return MusicUpdateInput.builder()
                .updateId(musicDto.getId())
                .updateCategoryId(musicDto.getId())
                .updateCategoryName(musicDto.getCategoryName())
                .updateName(musicDto.getName())
                .updateArtist(musicDto.getArtist())
                .updateUrl(musicDto.getUrl())
                .updateCover(musicDto.getCover())
                .updateLrc(musicDto.getLrc())
                .build();
    }
}
