package com.service.freeblog_admin.web.controller.user;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.util.ConstUtil;
import com.service.freeblog_admin.web.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자", description = "사용자 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserRestController {
    private final AdminUserService adminUserService;

    @Operation(summary = "이메일 사용가능 여부 확인", description = "이메일 사용 가능 여부 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 사용 가능"),
            @ApiResponse(responseCode = "500", description = "이미 사용 중으로 불가능")
    })
    @ResponseBody
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam(value = "email", required = false, defaultValue = "") String email) {
        try {
            adminUserService.checkSameEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 이메일 입니다.");
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog_admin-checkEmail] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BlogAdminUtil.getErrorMessage(exception));
        }
    }

    @Operation(summary = "id 사용가능 여부 확인", description = "id 사용 가능 여부 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "id 사용 가능"),
            @ApiResponse(responseCode = "500", description = "이미 사용 중으로 불가능")
    })
    @ResponseBody
    @GetMapping("/check-id")
    public ResponseEntity<String> checkId(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
        try {
            adminUserService.checkSameId(id);
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 id 입니다.");
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog_admin-checkId] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BlogAdminUtil.getErrorMessage(exception));
        }
    }
}
