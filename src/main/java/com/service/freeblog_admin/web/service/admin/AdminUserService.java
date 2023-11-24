package com.service.freeblog_admin.web.service.admin;

import com.service.freeblog_admin.config.admin.AdminUserConfig;
import com.service.freeblog_admin.web.domain.admin.AdminUser;
import com.service.freeblog_admin.web.dto.admin.AdminUserDto;
import com.service.freeblog_admin.web.dto.user.UserBasicInfoInput;
import com.service.freeblog_admin.web.dto.user.UserPasswordInput;
import com.service.freeblog_admin.web.dto.user.UserWithdrawInput;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminUserException;
import com.service.freeblog_admin.web.repository.admin.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;

    private final AdminUserConfig adminUserConfig;

    public boolean checkSameEmail(String email) {
        if (checkUserDomainByEmail(email)) {
            throw new AdminUserException(ServiceExceptionMessage.ALREADY_SAME_EMAIL.message());
        }
        return true;
    }

    public boolean checkSameId(String id) {
        if (checkUserDomainById(id)) {
            throw new AdminUserException(ServiceExceptionMessage.ALREADY_SAME_ID.message());
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser adminUser = findAdminUserByEmail(email);
        List<GrantedAuthority> grantedAuthorityList = new LinkedList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(adminUser.getEmail(), adminUser.getPassword(), grantedAuthorityList);
    }

    public AdminUser findAdminUserByEmail(String email) {
        return adminUserRepository.findAdminUserByEmail(email).orElseThrow(() -> new AdminUserException(ServiceExceptionMessage.ID_PW_WRONG.message()));
    }

    public AdminUserDto findAdminUserDto(String email) {
        return AdminUserDto.from(findAdminUserByEmail(email));
    }

    @Transactional
    public void signUp(AdminUser adminUser) {
        adminUserRepository.save(adminUser);
    }

    public boolean checkSecretKey(String secretKey) {
        return adminUserConfig.getSecretKey().equals(secretKey);
    }

    @Transactional
    public void withdraw(UserWithdrawInput userWithdrawInput) {
        AdminUser adminUser = adminUserRepository.findAdminUserByUserId(userWithdrawInput.getId()).orElseThrow(() -> new AdminUserException(ServiceExceptionMessage.ACCOUNT_INFO_NOT_FOUND.message()));
        String secretKey = userWithdrawInput.getKey();

        if (secretKey == null || secretKey.isEmpty() || adminUserConfig.getSecretKey().equals(secretKey) == false) {
            throw new AdminUserException(ServiceExceptionMessage.AUTH_VALID_KEY_MISMATCH.message());
        } else if (!BCrypt.checkpw(userWithdrawInput.getPassword(), adminUser.getPassword())) {
            throw new AdminUserException(ServiceExceptionMessage.MISMATCH_PASSWORD.message());
        }

        adminUserRepository.delete(adminUser);
    }

    @Transactional
    public void updateUserBasicInfo(UserBasicInfoInput userBasicInfoInput, String email) {
        AdminUser adminUser = adminUserRepository.findAdminUserByEmail(email).orElseThrow(() -> new AdminUserException(ServiceExceptionMessage.ACCOUNT_INFO_NOT_FOUND.message()));
        adminUser.setNickname(userBasicInfoInput.getNickname());
        adminUserRepository.save(adminUser);
    }

    @Transactional
    public void updatePassword(UserPasswordInput userPasswordInput) {
        AdminUser adminUser = adminUserRepository.findAdminUserByEmail(userPasswordInput.getEmail()).orElseThrow(() -> new AdminUserException(ServiceExceptionMessage.ACCOUNT_INFO_NOT_FOUND.message()));
        String secretKey = userPasswordInput.getKey();

        if (secretKey == null || secretKey.isEmpty() || adminUserConfig.getSecretKey().equals(secretKey) == false) {
            throw new AdminUserException(ServiceExceptionMessage.AUTH_VALID_KEY_MISMATCH.message());
        } else if (!userPasswordInput.getPassword().equals(userPasswordInput.getRePassword())) {
            throw new AdminUserException(ServiceExceptionMessage.RE_PASSWORD_MISMATCH.message());
        } else if (BCrypt.checkpw(userPasswordInput.getPassword(), adminUser.getPassword())) {
            throw new AdminUserException(ServiceExceptionMessage.COINCIDE_WITH_EACH_PASSWORD.message());
        }

        adminUser.setPassword(BCrypt.hashpw(userPasswordInput.getPassword(), BCrypt.gensalt()));
        adminUser.setUpdateTime(LocalDateTime.now());
        adminUserRepository.save(adminUser);
    }

    public boolean checkUserDomainByEmail(String email) {
        return adminUserRepository.findByEmail(email).isPresent();
    }

    public boolean checkUserDomainById(String id) {
        return adminUserRepository.findById(id).isPresent();
    }
}
