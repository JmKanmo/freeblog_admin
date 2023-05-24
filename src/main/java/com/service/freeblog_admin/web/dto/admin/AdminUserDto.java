package com.service.freeblog_admin.web.dto.admin;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.admin.AdminUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminUserDto {
    private final String userId;

    private final String email;

    private final String nickname;

    private final String registerTime;

    private final String updatedTime;

    public static AdminUserDto from(AdminUser adminUser) {
        return AdminUserDto.builder()
                .userId(adminUser.getUserId())
                .email(adminUser.getEmail())
                .nickname(adminUser.getNickname())
                .registerTime(BlogAdminUtil.formatLocalDateTimeToStr(adminUser.getRegisterTime()))
                .updatedTime(BlogAdminUtil.formatLocalDateTimeToStr(adminUser.getUpdateTime()))
                .build();
    }
}
