package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.dto.music.MusicCategoryDto;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicCategoryService {
    private final MusicCategoryRepository musicCategoryRepository;

    @Transactional
    public void musicCategoryAdd(MusicCategory musicCategory) {
        musicCategoryRepository.save(musicCategory);
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

    public MusicCategoryDto findMusicCategoryDtoById(long musicCategoryId) {
        Optional<MusicCategory> musicCategory = musicCategoryRepository.findById(musicCategoryId);

        if (musicCategory.isEmpty()) {
            return null;
        }
        return MusicCategoryDto.from(musicCategory.get());
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
