package com.service.freeblog_admin.web.domain.post;


import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.domain.blog.Blog;
import com.service.freeblog_admin.web.domain.category.Category;
import com.service.freeblog_admin.web.domain.comment.Comment;
import com.service.freeblog_admin.web.domain.tag.Tag;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category", "blog"})
@Table(name = "post", indexes = {
        @Index(name = "post_idx_seq", columnList = "seq")
})
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private Long seq;

    private String title;

    private String writer;

    private String thumbnailImage;

    private boolean isDelete;

    @Lob
    private String contents;

    private String uploadKey;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "post")
    private List<Tag> tagList;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
