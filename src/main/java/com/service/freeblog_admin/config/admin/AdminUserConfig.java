package com.service.freeblog_admin.config.admin;

import com.service.freeblog_admin.config.yaml.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:application-util.yml", factory = YamlPropertySourceFactory.class)
@Data
public class AdminUserConfig {
    @Value("${util-config.admin_user.secret_key}")
    private String secretKey;
}
