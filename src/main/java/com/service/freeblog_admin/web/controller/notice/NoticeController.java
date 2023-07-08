package com.service.freeblog_admin.web.controller.notice;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.dto.notice.NoticeUpdateDto;
import com.service.freeblog_admin.web.model.notice.NoticeUpdateInput;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.model.notice.NoticeInput;
import com.service.freeblog_admin.web.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "공지사항", description = "공지사항 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 페이지 반환", description = "공지사항 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 정보가 담긴 페이지")
    })
    @GetMapping
    public String notice(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        return "notice/notice";
    }

    @Operation(summary = "공지사항 작성 페이지 반환", description = "공지사항 작성 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 작성 페이지 반환")
    })
    @GetMapping("/write")
    public String noticeWrite(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("noticeInput", NoticeInput.builder().build());
        return "notice/notice-write";
    }

    @Operation(summary = "공지사항 수정 페이지 반환", description = "공지사항 수정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 페이지 반환")
    })
    @GetMapping("/update/{noticeId}")
    public String noticeUpdate(@PathVariable Long noticeId, Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        NoticeUpdateDto noticeUpdateDto = noticeService.findNoticeUpdateDtoById(noticeId);

        model.addAttribute("noticeUpdate", noticeUpdateDto);
        model.addAttribute("noticeInput", NoticeInput.builder().build());
        return "notice/notice-update";
    }

    @Operation(summary = "공지사항 목록 페이지 반환", description = "공지사항 목록 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 목록 정보가 담긴 페이지")
    })
    @GetMapping("/list")
    public String noticeList(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        return "notice/notice-list";
    }

    @Operation(summary = "공지사항 상세 페이지 반환", description = "공지사항 상세 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 정보가 담긴 페이지")
    })
    @GetMapping("/detail/{noticeId}")
    public String noticeDetail(@PathVariable Long noticeId, Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }

        model.addAttribute("noticeDetail", noticeService.findNoticeDetailDtoById(noticeId));
        return "notice/notice-detail";
    }

    @Operation(summary = "공지사항 수정", description = "공지사항 수정 진행 POST 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 작업 성공"),
            @ApiResponse(responseCode = "500", description = "DB 연결 오류, SQL 쿼리 수행 실패 등의 이유로 공지사항 수정 작업 실패")
    })
    @PostMapping("/update/{noticeId}")
    public String noticeUpdate(@PathVariable Long noticeId, @Valid NoticeUpdateInput noticeUpdateInput, Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        noticeService.updateNotice(noticeId, noticeUpdateInput);
        return String.format("redirect:/notice/detail/%d", noticeId);
    }
}
