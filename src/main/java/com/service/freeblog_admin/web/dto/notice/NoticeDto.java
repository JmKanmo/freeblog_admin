package com.service.freeblog_admin.web.dto.notice;

import com.service.freeblog_admin.web.domain.notice.Notice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeDto {
    private final long noticeId;
    private final String registerTime;
    private final String title;
    private final String summary;
    private final String contents;
    private final Boolean isBaseTimezone;
}
