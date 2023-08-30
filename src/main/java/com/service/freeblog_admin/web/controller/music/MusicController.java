package com.service.freeblog_admin.web.controller.music;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminException;
import com.service.freeblog_admin.web.model.music.MusicAddInput;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "음악", description = "음악 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/music")
@Slf4j
public class MusicController {
    @Operation(summary = "음악관리 페이지 반환", description = "음악관리 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악관리 정보가 담긴 관리 페이지")
    })
    @GetMapping("/admin")
    public String musicAdmin(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        return "music/music-admin";
    }

    @Operation(summary = "음악 추가 페이지 반환", description = "음악 추가 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 추가 정보가 담긴 관리 페이지")
    })
    @GetMapping("/add")
    public String musicAdd(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        model.addAttribute("musicAddInput", MusicAddInput.builder().build());
        return "music/music-add";
    }

    @Operation(summary = "음악 설정 페이지 반환", description = "음악 설정 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음악 설정 정보가 담긴 관리 페이지")
    })
    @GetMapping("/update")
    public String musicUpdate(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        if (!BlogAdminUtil.isAuth(authentication)) {
            throw new AdminException(ServiceExceptionMessage.NOT_AUTH_ACCESS.message());
        }
        return "music/music-update";
    }
}
