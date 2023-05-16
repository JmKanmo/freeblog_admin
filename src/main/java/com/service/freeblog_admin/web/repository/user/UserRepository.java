package com.service.freeblog_admin.web.repository.user;

import com.service.freeblog_admin.web.domain.user.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDomain, String> {
}
