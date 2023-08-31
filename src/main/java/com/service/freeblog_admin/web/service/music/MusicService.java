package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.web.domain.music.Music;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
import com.service.freeblog_admin.web.repository.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicCategoryService musicCategoryService;

    @Transactional
    public void musicAdd(MusicAddInput musicAddInput) {
        MusicCategory musicCategory = musicCategoryService.findMusicCategoryById(musicAddInput.getCategoryId());
        Music music = Music.from(musicAddInput, musicCategory);
        musicRepository.save(music);
    }
}
