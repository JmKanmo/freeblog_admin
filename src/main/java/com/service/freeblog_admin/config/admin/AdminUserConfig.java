package com.service.freeblog_admin.config.admin;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yml")
@Data
public class AdminUserConfig {
    @Value("${util-config.admin_user.secret_key}")
    private String secretKey;
}
