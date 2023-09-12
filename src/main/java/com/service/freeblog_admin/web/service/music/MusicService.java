package com.service.freeblog_admin.web.service.music;

import com.service.freeblog_admin.util.s3.AwsS3Service;
import com.service.freeblog_admin.web.domain.music.Music;
import com.service.freeblog_admin.web.domain.music.MusicCategory;
import com.service.freeblog_admin.web.domain.music.MusicS3;
import com.service.freeblog_admin.web.dto.music.MusicDto;
import com.service.freeblog_admin.web.dto.music.MusicS3Dto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.music.MusicManageException;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
import com.service.freeblog_admin.web.model.music.MusicDeleteInput;
import com.service.freeblog_admin.web.model.music.MusicUpdateInput;
import com.service.freeblog_admin.web.repository.music.MusicRepository;
import com.service.freeblog_admin.web.repository.music.MusicS3Mapper;
import com.service.freeblog_admin.web.repository.music.MusicS3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicService {
    private final MusicS3Mapper musicS3Mapper;
    private final MusicS3Repository musicS3Repository;
    private final MusicRepository musicRepository;
    private final MusicCategoryService musicCategoryService;
    private final AwsS3Service awsS3Service;

    @Transactional
    public void musicS3Add(MusicS3 musicS3) {
        musicS3Repository.save(musicS3);
    }

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

    @Transactional
    public MusicS3Dto musicAddS3(MultipartFile musicFile, MultipartFile musicImageFile, String uploadKey) throws Exception {
        String musicUrl = awsS3Service.uploadAudioFile(musicFile);
        String imageUrl = awsS3Service.uploadImageFile(musicImageFile);

        musicS3Add(MusicS3.builder()
                .musicUrl(musicUrl)
                .imageUrl(imageUrl)
                .uploadKey(uploadKey)
                .build());

        return MusicS3Dto.builder()
                .musicUrl(musicUrl)
                .imageUrl(imageUrl)
                .uploadKey(uploadKey)
                .build();
    }

    public List<MusicS3Dto> findMusicS3DtoByKeyword(String keyword) {
        return musicS3Mapper.searchMusicS3ByKeyword(keyword);
    }

    public MusicDto findMusicDtoById(Long musicId) {
        return MusicDto.from(musicRepository.findById(musicId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC.message())));
    }

    public Music findMusicById(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(() -> new MusicManageException(ServiceExceptionMessage.NOT_FOUND_MUSIC.message()));
    }
}
