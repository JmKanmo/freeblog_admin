package com.service.freeblog_admin.web.paging.notice;

import lombok.Data;

@Data
public class NoticePaginationResponse<T> {
    private T noticeTotalDto;
    private NoticePagination noticePagination;

    public NoticePaginationResponse(T noticeTotalDto, NoticePagination noticePagination) {
        this.noticeTotalDto = noticeTotalDto;
        this.noticePagination = noticePagination;
    }
}
