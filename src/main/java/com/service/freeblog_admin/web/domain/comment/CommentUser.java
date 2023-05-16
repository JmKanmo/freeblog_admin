package com.service.freeblog_admin.web.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUser {
    private String userProfileImage;
    private String targetUserId;
    private String targetUserNickname;
    private boolean isOwner;
    private String userId;
    private String userPassword;
    private String userNickname;
}

