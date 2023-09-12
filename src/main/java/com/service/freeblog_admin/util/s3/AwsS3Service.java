package com.service.freeblog_admin.util.s3;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.util.FileHandleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AwsS3Util awsS3Util;

    public String uploadImageFile(MultipartFile multipartFile) throws Exception {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.message());
        }

        String imgSrc = awsS3Util.storeImageFile(BlogAdminUtil.getImageFileUUID(multipartFile), multipartFile.getInputStream(), BlogAdminUtil.initObjectMetaData(multipartFile));
        return imgSrc;
    }

    public String uploadAudioFile(MultipartFile multipartFile) throws Exception {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            throw new FileHandleException(ServiceExceptionMessage.NOT_VALID_FILE_NAME.message());
        }

        String audioSrc = awsS3Util.storeAudioFile(BlogAdminUtil.getAudioFileUUID(multipartFile), multipartFile.getInputStream(), BlogAdminUtil.initObjectMetaData(multipartFile));
        return audioSrc;
    }
}
