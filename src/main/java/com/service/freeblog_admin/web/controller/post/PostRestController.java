package com.service.freeblog_admin.web.controller.post;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.util.ConstUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.post.PostManageException;
import com.service.freeblog_admin.web.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Tag(name = "포스트", description = "포스트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostRestController {
    private final PostService postService;

    @Operation(summary = "게시글 이미지 업로드", description = "게시글 이미지 업로드 수행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 이미지 업로드 완료"),
            @ApiResponse(responseCode = "500", description = "네트워크, 데이터베이스 저장 실패 등의 이유로 게시글 이미지 업로드 실패")
    })
    @PostMapping("/upload/post-image/{uploadKey}")
    public ResponseEntity<String> uploadPostImage(@RequestParam("compressed_post_image") MultipartFile multipartFile, Principal principal, @PathVariable String uploadKey) {
        try {
            if ((principal == null || principal.getName() == null)) {
                throw new PostManageException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }
            return ResponseEntity.status(HttpStatus.OK).body(postService.uploadSftpPostImage(multipartFile, uploadKey));
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog-uploadPostThumbnailImage] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("게시글 이미지 업로드에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }

    @Operation(summary = "게시글 이미지 삭제", description = "게시글 이미지 삭제 수행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 이미지 삭제 완료"),
            @ApiResponse(responseCode = "500", description = "네트워크, 데이터베이스 저장 실패 등의 이유로 게시글 이미지 삭제 실패")
    })
    @PostMapping("/delete/post-image")
    public ResponseEntity<String> deletePostImage(@RequestParam("imgSrcList") List<String> imgSrcList, Principal principal) {
        try {
            if ((principal == null || principal.getName() == null)) {
                throw new PostManageException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }
            postService.deleteSftpPostImage(imgSrcList);
            return ResponseEntity.status(HttpStatus.OK).body("게시글 이미지를 삭제하였습니다.");
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog-deletePostImage] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("게시글 이미지 삭제에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }
}
