package com.service.freeblog_admin.web.model.notice;

import com.service.freeblog_admin.util.ConstUtil;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class NoticeInput {
    @NotEmpty(message = "공지사항 제목이 비어있습니다.")
    @NotBlank(message = "공지사항 제목은 공백만 올 수 없습니다.")
    @Size(max = 128, message = "공지사항 제목은 최대 128글자 까지 작성 가능합니다.")
    private final String title;

    @NotEmpty(message = "공지사항 본문이 비어있습니다.")
    @NotBlank(message = "공지사항 본문은 공백만 올 수 없습니다.")
    @Size(max = ConstUtil.MAX_NOTICE_CONTENT_SIZE, message = "본문 크기가 허용 범위를 초과하였습니다.")
    private final String contents;

    /* 이미지 태그만 포함시키는 경우 고려 */
    @NotNull
//    @NotEmpty(message = "공지사항 요약이 비어있습니다.")
//    @NotBlank(message = "공지사항 요약은 공백만 올 수 없습니다.")
    private final String summary;

    @Size(max = 128, message = "업로드 키 정보는 최대 128글자 까지 작성 가능합니다.")
    private final String uploadKey;
}
