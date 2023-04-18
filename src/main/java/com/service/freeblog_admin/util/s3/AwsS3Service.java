package com.service.freeblog_admin.util.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AwsS3Util awsS3Util;
}
