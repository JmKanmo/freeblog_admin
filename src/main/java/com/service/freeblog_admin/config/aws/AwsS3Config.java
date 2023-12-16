package com.service.freeblog_admin.config.aws;

import com.service.freeblog_admin.config.yaml.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "yml")
@Data
public class AwsS3Config {
    @Value("${util-config.aws_s3-bucket.access_key_id}")
    private String accessKeyId;

    @Value("${util-config.aws_s3-bucket.secret_access_key_id}")
    private String secretAccessKeyId;

    @Value("${util-config.aws_s3-bucket.bucket_name}")
    private String bucketName;
}
