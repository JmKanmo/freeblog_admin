package com.service.freeblog_admin.web.error.model.util;

import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;

public class FileHandleException extends BlogAdminServiceException {
    public FileHandleException(String error) {
        super(error);
    }
}
