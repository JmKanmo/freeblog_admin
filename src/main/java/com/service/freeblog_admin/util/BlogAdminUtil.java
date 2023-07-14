package com.service.freeblog_admin.util;

import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BlogAdminUtil {
    public static String mappingRedirectUrl(String redirectUrl) {
//        if (redirectUrl.endsWith("/user/email-auth")
//                || redirectUrl.endsWith("/user/update/password")) {
//            return "/";
//        }
        return redirectUrl;
    }

    public static String createRandomAlphaNumberString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String createRandomString(int length) {
        return RandomStringUtils.random(length);
    }

    public static String redirect(String url) {
        return String.format("redirect:%s", url);
    }

    public static boolean isAuth(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    public static String getLoginFailMessage(AuthenticationException exception) {
        if (exception instanceof AuthenticationServiceException) {
            return ServiceExceptionMessage.FAILED_AUTHENTICATION.message();
        } else if (exception instanceof BadCredentialsException) {
            return ServiceExceptionMessage.ID_PW_WRONG.message();

        } else if (exception instanceof LockedException) {
            return ServiceExceptionMessage.LOCK_ACCOUNT.message();

        } else if (exception instanceof DisabledException) {
            return ServiceExceptionMessage.DEACTIVATE_ACCOUNT.message();

        } else if (exception instanceof AccountExpiredException) {
            return ServiceExceptionMessage.EXPIRED_ACCOUNT.message();

        } else if (exception instanceof CredentialsExpiredException) {
            return ServiceExceptionMessage.EXPIRED_PASSWORD.message();
        }
        return exception.getMessage();
    }

    public static LocalDateTime nowByZoneId() {
        return LocalDateTime.now(ZoneId.of(ConstUtil.ASIA_SEOUL));
    }

    public static String getImageFileUUID(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uuid = LocalDateTime.now().toString() + UUID.nameUUIDFromBytes(fileName.getBytes(StandardCharsets.UTF_8)) + "." + extension;
        return uuid;
    }

    public static String getImageFileUUIDBySftp(MultipartFile multipartFile) {
        String fileName = multipartFile.getContentType();
        String extension = getImageException(fileName.substring(fileName.lastIndexOf("/") + 1));
        String uuid = UUID.randomUUID() + "." + extension;
        return uuid;
    }

    public static String getImageException(String extension) {
        if (extension == null || extension.isEmpty()) {
            return "png";
        }

        switch (extension) {
            case "jpeg":
                return "jpg";
            case "GIF":
                return "gif";
            default:
                return extension;
        }
    }

    public static String getErrorMessage(Exception exception) {
        if (exception instanceof BlogAdminServiceException) {
            return exception.getMessage();
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            return "세션이 만료되어 작업에 실패하였습니다.";
        } else if (exception instanceof DataAccessException) {
            return "데이터베이스 쿼리 수행에 실패하였습니다.";
        }
        return ConstUtil.UNDEFINED_ERROR;
    }

    public static String formatLocalDateTimeToStr(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return localDateTime != null ? localDateTime.format(formatter) : "";
    }

    public static String formatLocalDateTimeToStrByPattern(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime != null ? localDateTime.format(formatter) : "";
    }

    public static String formatLocalDateTimeToStr(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime != null ? localDateTime.format(formatter) : "";
    }
}
