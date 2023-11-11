package com.service.freeblog_admin.web.dto.music;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicCategoryDto {
    private final Long id;
    private final String name;
    private final String registerTime;
    private final String updateTime;
    private final Boolean isBaseTimezone;

    public static MusicCategoryDto from(MusicCategory musicCategory) {
        return MusicCategoryDto.builder()
                .id(musicCategory.getId())
                .name(musicCategory.getName())
                .registerTime(BlogAdminUtil.formatLocalDateTimeToStrByPattern(musicCategory.getRegisterTime(), "yyyy.MM.dd HH:mm:ss"))
                .updateTime(BlogAdminUtil.formatLocalDateTimeToStrByPattern(musicCategory.getUpdateTime(), "yyyy.MM.dd HH:mm:ss"))
                .isBaseTimezone(musicCategory.getIsBaseTimezone())
                .build();
    }
}
