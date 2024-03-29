package com.service.freeblog_admin.web.domain.music;

import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.model.music.MusicCategoryInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "music_category")
public class MusicCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_category_id")
    private Long id;

    private String name;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "musicCategory")
    private List<Music> musicList;

    public static MusicCategory from(MusicCategoryInput musicCategoryInput) {
        return MusicCategory.builder()
                .name(musicCategoryInput.getName())
                .isBaseTimezone(true)
                .build();
    }
}
