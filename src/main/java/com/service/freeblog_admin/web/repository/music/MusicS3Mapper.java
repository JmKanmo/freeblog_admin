package com.service.freeblog_admin.web.repository.music;

import com.service.freeblog_admin.web.dto.music.MusicS3Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface MusicS3Mapper {
    List<MusicS3Dto> searchMusicS3ByKeyword(String keyword);
}
