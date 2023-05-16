package com.service.freeblog_admin.web.domain.category;

import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.domain.blog.Blog;
import com.service.freeblog_admin.web.domain.post.Post;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "blog")
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private Long parentId;

    @Column(length = 25)
    private String name;

    private Long seq;

    private boolean isDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "category")
    private List<Post> postList;
}