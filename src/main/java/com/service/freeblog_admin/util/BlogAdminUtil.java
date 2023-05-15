package com.service.freeblog_admin.util;

import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public class BlogAdminUtil {
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

}
