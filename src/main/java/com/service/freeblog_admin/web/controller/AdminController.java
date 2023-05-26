package com.service.freeblog_admin.web.controller;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.dto.admin.AdminUserDto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.AdminException;
import com.service.freeblog_admin.web.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Tag(name = "어드민", description = "어드민 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
@Slf4j
public class AdminController {


    @Operation(summary = "블로그 페이지 반환", description = "블로그 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "블로그,사용자 정보가 담긴 블로그 페이지")
    })
    @GetMapping
    public String blog(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        return "admin";
    }
}
