package com.service.freeblog_admin.web.error.model.admin;

import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;

public class AdminException extends BlogAdminServiceException {
    public AdminException(String error) {
        super(error);
    }
}
