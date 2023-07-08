package com.service.freeblog_admin.web.dto.notice;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.notice.Notice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeUpdateDto {
    private Long noticeId;
    private final String title;
    private final String contents;
    private String summary;
    private String uploadKey;
    private final String registerTime;
    private final String updateTime;

    public static NoticeUpdateDto from(Notice notice) {
        return NoticeUpdateDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .summary(notice.getSummary())
                .uploadKey(notice.getUploadKey())
                .registerTime(BlogAdminUtil.formatLocalDateTimeToStr(notice.getRegisterTime()))
                .updateTime(BlogAdminUtil.formatLocalDateTimeToStr(notice.getUpdateTime()))
                .build();
    }
}
