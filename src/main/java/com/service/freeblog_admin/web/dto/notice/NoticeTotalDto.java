package com.service.freeblog_admin.web.dto.notice;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class NoticeTotalDto {
    private final Integer totalCount;
    private final List<NoticeDto> noticeDtoList;

    public static NoticeTotalDto from(List<NoticeDto> noticeDtoList) {
        if (noticeDtoList == null || noticeDtoList.isEmpty()) {
            return NoticeTotalDto.builder()
                    .totalCount(0)
                    .noticeDtoList(Collections.emptyList())
                    .build();
        } else {
            return NoticeTotalDto.builder()
                    .totalCount(noticeDtoList.size())
                    .noticeDtoList(noticeDtoList)
                    .build();
        }
    }
}
