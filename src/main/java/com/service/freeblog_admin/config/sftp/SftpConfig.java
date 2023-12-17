package com.service.freeblog_admin.config.sftp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yml")
@Data
public class SftpConfig {
    @Value("${util-config.aws_ec2_sftp.protocol}")
    private String protocol;

    @Value("${util-config.aws_ec2_sftp.ip}")
    private String ip;

    @Value("${util-config.aws_ec2_sftp.port}")
    private Integer port;

    @Value("${util-config.aws_ec2_sftp.id}")
    private String id;

    @Value("${util-config.aws_ec2_sftp.password}")
    private String password;

    @Value("${util-config.aws_ec2_sftp.url}")
    private String url;

    @Value("${util-config.aws_ec2_sftp.directory}")
    private String directory;

    @Value("${util-config.aws_ec2_sftp.timeout}")
    private Integer timeout;
}
