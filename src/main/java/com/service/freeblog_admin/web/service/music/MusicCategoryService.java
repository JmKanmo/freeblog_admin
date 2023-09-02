package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.web.domain.music.Music;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.dto.music.MusicCategoryDto;
import com.service.freeblog_admin.web.dto.music.MusicDto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.music.MusicManageException;
import com.service.freeblog_admin.web.model.music.MusicCategoryDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicCategoryUpdateInput;
import com.service.freeblog_admin.web.repository.music.MusicCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicCategoryService {
    private final MusicCategoryRepository musicCategoryRepository;

    @Transactional
    public void musicCategoryAdd(MusicCategory musicCategory) {
        musicCategoryRepository.save(musicCategory);
    }

    public List<MusicDto> findMusicDtoByCategoryId(Long musicCategoryId) {
        MusicCategory musicCategory = findMusicCategoryById(musicCategoryId);
        List<Music> musicList = musicCategory.getMusicList();
        return musicList.stream().map(MusicDto::from).collect(Collectors.toList());
    }

    public List<MusicCategoryDto> findMusicCategoryDtoList() {
        List<MusicCategory> musicCategories = musicCategoryRepository.findAll();
        List<MusicCategoryDto> musicCategoryDtos = new ArrayList<>();

        if (musicCategories == null || musicCategories.isEmpty()) {
            return musicCategoryDtos;
        }

        for (MusicCategory musicCategory : musicCategories) {
            musicCategoryDtos.add(MusicCategoryDto.from(musicCategory));
        }
        return musicCategoryDtos;
    }

    public MusicCategory findMusicCategoryById(Long musicCategoryId) {
        return musicCategoryRepository.findById(musicCategoryId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC_CATEGORY.message()));
    }

    public MusicCategoryDto findMusicCategoryDtoById(long musicCategoryId) {
        return MusicCategoryDto.from(musicCategoryRepository.findById(musicCategoryId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC_CATEGORY.message())));
    }

    @Transactional
    public void updateMusicCategory(MusicCategoryUpdateInput musicCategoryUpdateInput) {
        MusicCategory musicCategory = musicCategoryRepository.findById(musicCategoryUpdateInput.getUpdateId()).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC_CATEGORY.message()));
        musicCategory.setName(musicCategoryUpdateInput.getUpdateName());
    }

    @Transactional
    public void deleteMusicCategory(MusicCategoryDeleteInput musicCategoryDeleteInput) {
        MusicCategory musicCategory = musicCategoryRepository.findById(musicCategoryDeleteInput.getDeleteId()).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC_CATEGORY.message()));
        musicCategoryRepository.delete(musicCategory);
    }
}
