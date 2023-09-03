package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.web.domain.music.Music;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.dto.music.MusicDto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.music.MusicManageException;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
import com.service.freeblog_admin.web.model.music.MusicDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicUpdateInput;
import com.service.freeblog_admin.web.repository.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public void musicUpdate(MusicUpdateInput musicUpdateInput) {
        MusicCategory musicCategory = musicCategoryService.findMusicCategoryById(musicUpdateInput.getUpdateCategoryId());
        Music music = findMusicById(musicUpdateInput.getUpdateId());
        music.update(musicUpdateInput, musicCategory);
    }

    @Transactional
    public void musicDelete(MusicDeleteInput musicDeleteInput) {
        Music music = findMusicById(musicDeleteInput.getDeleteId());
        musicRepository.delete(music);
    }

    public MusicDto findMusicDtoById(Long musicId) {
        return MusicDto.from(musicRepository.findById(musicId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC.message())));
    }

    public Music findMusicById(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC.message()));
    }
}
