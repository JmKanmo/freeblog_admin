package com.service.freeblog_admin.web.repository.report;

import com.service.freeblog_admin.web.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
