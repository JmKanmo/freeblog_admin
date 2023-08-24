package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.repository.music.MusicCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MusicCategoryService {
    private final MusicCategoryRepository musicCategoryRepository;

    @Transactional
    public void musicCategoryAdd(MusicCategory musicCategory) {
        musicCategoryRepository.save(musicCategory);
    }
}
