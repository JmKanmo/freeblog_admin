package com.service.freeblog_admin.web.repository.admin;

import com.service.freeblog_admin.web.domain.admin.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
    Optional<AdminUser> findAdminUserByEmail(String email);

    Optional<AdminUser> findAdminUserByUserId(String userId);

    Optional<AdminUser> findByEmail(String email);
}
