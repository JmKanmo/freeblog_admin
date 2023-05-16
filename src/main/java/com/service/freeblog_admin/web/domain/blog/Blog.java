package com.service.freeblog_admin.web.domain.blog;

import com.service.freeblog_admin.web.domain.category.Category;
import com.service.freeblog_admin.web.domain.post.Post;
import com.service.freeblog_admin.web.domain.user.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    private String name;

    private boolean isDelete;

    @Lob
    private String intro;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "blog")
    private UserDomain user;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog")
    private List<Category> categoryList;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "blog")
    private List<Post> postList;
}
