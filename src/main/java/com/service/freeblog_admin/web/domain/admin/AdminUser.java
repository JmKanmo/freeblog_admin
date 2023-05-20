package com.service.freeblog_admin.web.domain.admin;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AdminUser", indexes = {
        @Index(name = "user_idx_email", columnList = "email")
})
public class AdminUser {
    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String name;
}
