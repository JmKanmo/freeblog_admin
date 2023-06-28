package com.service.freeblog_admin.web.repository.notice;

import com.service.freeblog_admin.web.dto.notice.NoticeDto;
import com.service.freeblog_admin.web.dto.notice.NoticeSearchPagingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int findNoticeCount();

    List<NoticeDto> findNoticeDtoListByPaging(NoticeSearchPagingDto noticeSearchPagingDto);
}
