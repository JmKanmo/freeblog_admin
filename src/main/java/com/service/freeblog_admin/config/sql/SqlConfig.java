package com.service.freeblog_admin.config.sql;

import com.service.freeblog_admin.config.yaml.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yml")
@Data
public class SqlConfig {
    @Value("${util-config.sql_config.sql_search_pattern}")
    private String sqlSearchPattern;
}

