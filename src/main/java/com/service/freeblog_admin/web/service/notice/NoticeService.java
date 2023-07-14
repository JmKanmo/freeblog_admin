package com.service.freeblog_admin.web.service.notice;

import com.service.freeblog_admin.web.domain.notice.Notice;
import com.service.freeblog_admin.web.domain.notice.NoticeAlarm;
import com.service.freeblog_admin.web.dto.notice.*;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.notice.NoticeManageException;
import com.service.freeblog_admin.web.model.notice.NoticeUpdateInput;
import com.service.freeblog_admin.web.paging.notice.NoticePagination;
import com.service.freeblog_admin.web.paging.notice.NoticePaginationResponse;
import com.service.freeblog_admin.web.repository.notice.NoticeAlarmRepository;
import com.service.freeblog_admin.web.repository.notice.NoticeMapper;
import com.service.freeblog_admin.web.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeAlarmRepository noticeAlarmRepository;
    private final NoticeMapper noticeMapper;

    @Transactional
    public void registerNotice(Notice notice) {
        Notice createdNotice = noticeRepository.save(notice);
        noticeAlarmRepository.save(NoticeAlarm.from(Objects.hashCode(createdNotice), createdNotice.getTitle()));
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

    public NoticeUpdateDto findNoticeUpdateDtoById(Long noticeId) {
        return NoticeUpdateDto.from(noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeManageException(ServiceExceptionMessage.NOT_FOUND_NOTIFICATION.message())));
    }

    @Transactional
    public void updateNotice(Long noticeId, NoticeUpdateInput noticeUpdateInput) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NoticeManageException(ServiceExceptionMessage.NOT_FOUND_NOTIFICATION.message()));
        notice.setTitle(noticeUpdateInput.getTitle());
        notice.setContents(noticeUpdateInput.getContents());
        notice.setSummary(noticeUpdateInput.getSummary());
        notice.setUploadKey(noticeUpdateInput.getUploadKey());
    }
}
