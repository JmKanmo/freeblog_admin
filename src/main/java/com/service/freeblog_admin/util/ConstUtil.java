package com.service.freeblog_admin.util;

public class ConstUtil {
    public static final String AWS_S3_IMAGE_URL = "https://freelog-s3-bucket.s3.amazonaws.com/image/%s";
    public static final String AWS_S3_AUDIO_URL = "https://freelog-s3-bucket.s3.amazonaws.com/audio/%s";
    public static final String AUTHENTICATION_MESSAGE = "FLASH_AUTHENTICATION_MESSAGE";
    public static final String UNDEFINED = "<<<undefined>>>";
    public static final String UNDEFINED_ERROR = "UNDEFINED-ERROR";
    public static final String SFTP_IMAGE_URL = "http://%s/%s"; // 추후에 https 설정 및 변경
    public static final int MAX_NOTICE_CONTENT_SIZE = 5 * 1024 * 1024; // 5MB

    public static final int SFTP_POST_IMAGE_HASH = "post-image".hashCode();
    public static final String ASIA_SEOUL = "Asia/Seoul";

    public static final String TOTAL = "전체";
}
