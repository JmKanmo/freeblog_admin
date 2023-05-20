package com.service.freeblog_admin.web.service.admin;

import com.service.freeblog_admin.web.domain.admin.AdminUser;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.AdminUserException;
import com.service.freeblog_admin.web.repository.admin.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findAdminUserByEmail(email).orElseThrow(() -> new AdminUserException(ServiceExceptionMessage.ID_PW_WRONG.message()));
        List<GrantedAuthority> grantedAuthorityList = new LinkedList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(adminUser.getEmail(), adminUser.getPassword(), grantedAuthorityList);
    }

    @Transactional
    public void signUp(AdminUser adminUser) {
        adminUserRepository.save(adminUser);
    }

    @Transactional
    public void withdraw(AdminUser adminUser) {
        adminUserRepository.delete(adminUser);
    }
}
