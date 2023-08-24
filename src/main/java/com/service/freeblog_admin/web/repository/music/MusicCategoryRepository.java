package com.service.freeblog_admin.web.repository.music;

import com.service.freeblog_admin.web.domain.music.MusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicCategoryRepository extends JpaRepository<MusicCategory, Long> {
}
