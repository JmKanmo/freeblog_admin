package com.service.freeblog_admin.util.sftp;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.util.FileHandleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SftpService {
    private final SftpUtil sftpUtil;

    public String sftpImageFileUpload(MultipartFile multipartFile, Object hash, Object id) throws Exception {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.message());
        }

        String imgSrc = sftpUtil.fileUpload(BlogAdminUtil.getImageFileUUIDBySftp(multipartFile), multipartFile.getInputStream(), "images", String.valueOf(hash), String.valueOf(id), BlogAdminUtil.formatLocalDateTimeToStrByPattern(BlogAdminUtil.nowByZoneId(), "yyyy-MM-dd"));
        return imgSrc;
    }

    public void sftpImageFileDelete(String imgSrc) throws Exception {
        if (imgSrc == null || imgSrc.isEmpty()) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.name());
        }

        String[] parsed = parsedSftpImgSrc(imgSrc);
        sftpUtil.deleteFile(parsed[1], parsed[2], parsed[3], parsed[4], parsed[5]);
    }

    private String[] parsedSftpImgSrc(String imgSrc) {
        String[] parsed = imgSrc.split("://");

        if (parsed.length <= 1) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.name());
        }

        parsed = parsed[1].split("/");

        if (parsed.length < 6) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.name());
        }

        return parsed;
    }
}
