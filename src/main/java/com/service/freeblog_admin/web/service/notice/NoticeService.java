package com.service.freeblog_admin.web.service.notice;

import com.service.freeblog_admin.web.domain.notice.Notice;
import com.service.freeblog_admin.web.repository.notice.NoticeMapper;
import com.service.freeblog_admin.web.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
