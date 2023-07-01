package com.service.freeblog_admin.web.service.notice;

import com.service.freeblog_admin.web.domain.notice.Notice;
import com.service.freeblog_admin.web.dto.notice.NoticeDetailDto;
import com.service.freeblog_admin.web.dto.notice.NoticeDto;
import com.service.freeblog_admin.web.dto.notice.NoticeSearchPagingDto;
import com.service.freeblog_admin.web.dto.notice.NoticeTotalDto;
import com.service.freeblog_admin.web.paging.notice.NoticePagination;
import com.service.freeblog_admin.web.paging.notice.NoticePaginationResponse;
import com.service.freeblog_admin.web.repository.notice.NoticeMapper;
import com.service.freeblog_admin.web.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    @Transactional
    public void registerNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public NoticePaginationResponse<NoticeTotalDto> findTotalPaginationNotice(NoticeSearchPagingDto noticeSearchPagingDto) {
        int noticeCount = noticeMapper.findNoticeCount();
        NoticePagination noticePagination = new NoticePagination(noticeCount, noticeSearchPagingDto);
        noticeSearchPagingDto.setNoticePagination(noticePagination);
        List<NoticeDto> noticeDtoList = noticeMapper.findNoticeDtoListByPaging(noticeSearchPagingDto);
        return new NoticePaginationResponse<>(NoticeTotalDto.from(noticeDtoList), noticePagination);
    }

    public NoticeDetailDto findNoticeDetailDtoById(Long noticeId) {
        return noticeMapper.findNoticeDetailDtoById(noticeId);
    }
}
