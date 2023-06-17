package com.service.freeblog_admin.web.error.model.admin;

import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;

public class AdminUserException extends BlogAdminServiceException {
    public AdminUserException(String error) {
        super(error);
    }
}
