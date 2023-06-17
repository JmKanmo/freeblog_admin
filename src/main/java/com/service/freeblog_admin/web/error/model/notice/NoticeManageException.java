package com.service.freeblog_admin.web.error.model.notice;

import com.service.freeblog_admin.web.error.model.BlogAdminServiceException;

public class NoticeManageException extends BlogAdminServiceException {
    public NoticeManageException(String error) {
        super(error);
    }
}
