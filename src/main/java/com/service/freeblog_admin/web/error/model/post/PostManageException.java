package com.service.freeblog_admin.web.error.model.post;

import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;

public class PostManageException extends BlogAdminServiceException {
    public PostManageException(String error) {
        super(error);
    }
}
