package com.service.freeblog_admin.web.dto.notice;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.paging.notice.NoticePaginationResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class NoticePagingResponseDto {
    private final String message;
    private final int responseCode;
    private final NoticePaginationResponse<NoticeTotalDto> noticePaginationResponse;

    public static NoticePagingResponseDto success(NoticePaginationResponse<NoticeTotalDto> noticePaginationResponse) {
        return NoticePagingResponseDto.builder()
                .responseCode(HttpStatus.OK.value())
                .noticePaginationResponse(noticePaginationResponse)
                .message("success")
                .build();
    }

    public static NoticePagingResponseDto fail(Exception exception) {
        return NoticePagingResponseDto.builder()
                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .noticePaginationResponse(null)
                .message(String.format("fail: %s", BlogAdminUtil.getErrorMessage(exception)))
                .build();
    }
}
