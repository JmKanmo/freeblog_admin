package com.service.freeblog_admin.web.controller.notice;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.util.ConstUtil;
import com.service.freeblog_admin.web.domain.notice.Notice;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.error.model.notice.NoticeManageException;
import com.service.freeblog_admin.web.model.notice.NoticeInput;
import com.service.freeblog_admin.web.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Tag(name = "공지사항", description = "공지사항 관련 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Slf4j
public class NoticeRestController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록 API", description = "공지사항 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 정상 등록"),
            @ApiResponse(responseCode = "500", description = "공지사항 등록 실패"),
    })
    @PostMapping("/write")
    public ResponseEntity<String> writeNotice(@Valid NoticeInput noticeInput, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (principal == null || principal.getName() == null) {
                throw new AdminException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }

            if (bindingResult.hasErrors()) {
                throw new NoticeManageException(ServiceExceptionMessage.NOT_VALID_FORM_INPUT.message());
            }

            Notice notice = Notice.from(noticeInput);
            noticeService.registerNotice(notice);
            return ResponseEntity.status(HttpStatus.OK).body("공지사항 작성이 완료되었습니다. 작성 된 공지사항은 공지사항 목록에서 확인 바랍니다.");
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog_admin-writeNotice] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("공지 업로드에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }

    @Operation(summary = "공지사항 페이징 기반 반환 API", description = "공지사항 페이징 기반 반환 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 정상 반환"),
            @ApiResponse(responseCode = "500", description = "공지사항 반환 실패"),
    })
    @PostMapping("/list-page")
    public ResponseEntity<?> listNotice(Principal principal) {
        try {
            if (principal == null || principal.getName() == null) {
                throw new AdminException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }
            //  TODO
            return ResponseEntity.status(HttpStatus.OK).body("공지사항 반환 완료");
        } catch (Exception exception) {
            if (BlogAdminUtil.getErrorMessage(exception) == ConstUtil.UNDEFINED_ERROR) {
                log.error("[freeblog_admin-listNotice] exception occurred ", exception);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("공지사항 반환에 실패하였습니다. %s", BlogAdminUtil.getErrorMessage(exception)));
        }
    }
}
