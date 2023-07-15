package com.service.freeblog_admin.web.repository.notice;

import com.service.freeblog_admin.web.dto.notice.NoticeDetailDto;
import com.service.freeblog_admin.web.dto.notice.NoticeDto;
import com.service.freeblog_admin.web.dto.notice.NoticeSearchPagingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int findNoticeCount();

    int findNoticeCountByKeyword(String searchType, String keyword);

    List<NoticeDto> findNoticeDtoListByPaging(@Param("noticeSearchPagingDto") NoticeSearchPagingDto noticeSearchPagingDto);

    NoticeDetailDto findNoticeDetailDtoById(Long noticeId);
}
