package com.service.freeblog_admin.web.controller.user;

import com.service.freeblog_admin.util.BlogAdminUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "사용자", description = "사용자 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Operation(summary = "로그인 페이지", description = "로그인 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 페이지")
    })
    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication authentication) {
        if (BlogAdminUtil.isAuth(authentication)) {
            return BlogAdminUtil.redirect("/");
        }
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);
        return "user/login";
    }
}
