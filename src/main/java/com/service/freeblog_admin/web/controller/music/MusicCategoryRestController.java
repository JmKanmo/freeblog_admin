package com.service.freeblog_admin.web.controller.music;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.service.music.MusicCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-category")
@Slf4j
public class MusicCategoryRestController {
    private final MusicCategoryService musicCategoryService;

    @Operation(summary = "카테고리 해당 음악 목록 데이터 반환", description = "카테고리 해당 음악 목록 데이터 반환 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 해당 음악 목록 데이터 반환 작업 완료"),
            @ApiResponse(responseCode = "500", description = "네트워크, 데이터베이스 저장 실패 등의 이유로 카테고리 해당 음악 목록 데이터 반환 작업 실패")
    })
    @GetMapping("/search/music/{categoryId}")
    public ResponseEntity<?> searchMusic(@PathVariable Long categoryId, Authentication authentication) throws Exception {
        try {
            if (!BlogAdminUtil.isAuth(authentication)) {
                throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
            }
            return ResponseEntity.status(HttpStatus.OK).body(musicCategoryService.findMusicDtoByCategoryId(categoryId));
        } catch (Exception exception) {
            log.error("[MusicCategoryRestController:searchMusic] exception occured ", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("뮤직 데이터 반환에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }
}
