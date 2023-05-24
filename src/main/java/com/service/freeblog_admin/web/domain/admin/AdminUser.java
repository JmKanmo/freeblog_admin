package com.service.freeblog_admin.web.domain.admin;


import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.model.admin.AdminUserSignupInput;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AdminUser", indexes = {
        @Index(name = "user_idx_email", columnList = "email")
})
public class AdminUser extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String nickname;

    public static AdminUser from(AdminUserSignupInput adminUserSignupInput) {
        return AdminUser.builder()
                .userId(adminUserSignupInput.getId())
                .email(adminUserSignupInput.getEmail())
                .nickname(adminUserSignupInput.getNickname())
                .password(BCrypt.hashpw(adminUserSignupInput.getPassword(), BCrypt.gensalt()))
                .build();
    }
}
