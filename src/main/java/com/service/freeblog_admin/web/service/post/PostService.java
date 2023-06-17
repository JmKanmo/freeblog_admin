package com.service.freeblog_admin.web.service.post;

import com.service.freeblog_admin.util.ConstUtil;
import com.service.freeblog_admin.util.sftp.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final SftpService sftpService;

    public String uploadSftpPostImage(MultipartFile multipartFile, String uploadKey) throws Exception {
        return sftpService.sftpImageFileUpload(multipartFile, ConstUtil.SFTP_POST_IMAGE_HASH, uploadKey);
    }

    public void deleteSftpPostImage(List<String> imgSrcList) throws Exception {
        for (String imgSrc : imgSrcList) {
            sftpService.sftpImageFileDelete(imgSrc);
        }
    }
}
