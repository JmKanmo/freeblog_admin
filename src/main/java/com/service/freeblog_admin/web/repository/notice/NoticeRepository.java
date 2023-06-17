package com.service.freeblog_admin.web.repository.notice;

import com.service.freeblog_admin.web.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
