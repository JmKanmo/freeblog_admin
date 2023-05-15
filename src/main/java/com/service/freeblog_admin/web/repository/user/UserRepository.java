package com.service.freeblog_admin.web.repository.user;

import com.service.freeblog_admin.web.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
