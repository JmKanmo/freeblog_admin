package com.service.freeblog_admin.web.controller.music.rest;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.service.music.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
@Slf4j
public class MusicRestController {
    private final MusicService musicService;

    @Operation(summary = "음악 상세 데이터 반환", description = "음악 상세 데이터 반환 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 상세 데이터 반환 작업 완료"),
            @ApiResponse(responseCode = "500", description = "네트워크, 데이터베이스 저장 실패 등의 이유로 음악 상세 데이터 반환 작업 실패")
    })
    @GetMapping("/search/{musicId}")
    public ResponseEntity<?> searchMusic(@PathVariable Long musicId, Authentication authentication) throws Exception {
        try {
            if (!BlogAdminUtil.isAuth(authentication)) {
                throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
            }
            return ResponseEntity.status(HttpStatus.OK).body(musicService.findMusicDtoById(musicId));
        } catch (Exception exception) {
            log.error("[MusicRestController:searchMusic] exception occured ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("뮤직 데이터 반환에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }

    @Operation(summary = "키워드 기반 뮤직 S3 조회 데이터 반환", description = "키워드 기반 뮤직 S3 데이터 반환 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "키워드 기반 뮤직 S3 조회 데이터 반환 작업 완료"),
            @ApiResponse(responseCode = "500", description = "네트워크, 데이터베이스 저장 실패 등의 이유로 키워드 기반 뮤직 S3 조회 데이터 반환 작업 실패")
    })
    @GetMapping("/search/s3/{keyword}")
    public ResponseEntity<?> searchMusicS3ByKeyword(@PathVariable String keyword, Authentication authentication) throws Exception {
        try {
            if (!BlogAdminUtil.isAuth(authentication)) {
                throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
            }
            return ResponseEntity.status(HttpStatus.OK).body(musicService.findMusicS3DtoByKeyword(keyword));
        } catch (Exception exception) {
            log.error("[MusicRestController:searchMusicS3ByKeyword] exception occured ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("키워드 기반 뮤직 S3 데이터 반환에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }

    @Operation(summary = "음악 S3 추가 작업", description = "음악 S3 추가 작업 진행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 S3 추가 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, S3 연동 실패,  SQL 쿼리 수행 실패 등의 이유로 음악 S3 추가 작업 실패")
    })
    @PostMapping("/add/s3")
    public ResponseEntity<?> musicAddS3(
            @RequestParam("musicFile") MultipartFile musicFile,
            @RequestParam("musicImage") MultipartFile musicImageFile,
            @RequestParam("uploadKey") String uploadKey,
            Authentication authentication) {
        try {
            if (!BlogAdminUtil.isAuth(authentication)) {
                throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
            }
            return ResponseEntity.status(HttpStatus.OK).body(musicService.musicAddS3(musicFile, musicImageFile, uploadKey));
        } catch (Exception exception) {
            log.error("[MusicRestController:musicAddS3] exception occured ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("뮤직 데이터 S3 업로드에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }
}
