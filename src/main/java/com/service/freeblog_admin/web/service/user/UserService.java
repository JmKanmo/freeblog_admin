package com.service.freeblog_admin.web.service.user;

import com.service.freeblog_admin.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    // TODO 사용자 정보 불러오기 등등 ...
}
