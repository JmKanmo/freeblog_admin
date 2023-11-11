package com.service.freeblog_admin.web.domain.music;

import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
import com.service.freeblog_admin.web.model.music.MusicUpdateInput;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music")
public class Music extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private String name;

    private String artist;

    private String url;

    private String cover;

    private String lrc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_category_id")
    private MusicCategory musicCategory;

    public static Music from(MusicAddInput musicAddInput, MusicCategory musicCategory) {
        return Music.builder()
                .name(musicAddInput.getName())
                .artist(musicAddInput.getArtist())
                .url(musicAddInput.getUrl())
                .cover(musicAddInput.getCover())
                .lrc(musicAddInput.getLrc())
                .musicCategory(musicCategory)
                .isBaseTimezone(true)
                .build();
    }

    public void update(MusicUpdateInput musicUpdateInput, MusicCategory musicCategory) {
        this.name = musicUpdateInput.getUpdateName();
        this.artist = musicUpdateInput.getUpdateArtist();
        this.url = musicUpdateInput.getUpdateUrl();
        this.cover = musicUpdateInput.getUpdateCover();
        this.lrc = musicUpdateInput.getUpdateUrl();
        this.musicCategory = musicCategory;
    }
}
