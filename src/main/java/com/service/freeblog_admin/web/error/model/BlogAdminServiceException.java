package com.service.freeblog_admin.web.error.model;

public class BlogAdminServiceException extends RuntimeException {
    public BlogAdminServiceException(String error) {
        super(error);
    }
}
