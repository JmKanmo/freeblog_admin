package com.service.freeblog_admin.web.controller;

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

@Tag(name = "어드민", description = "어드민 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
@Slf4j
public class AdminController {


    @Operation(summary = "어드민 페이지 반환", description = "어드민 페이지를 반환하는 GET 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "어드민 정보가 담긴 페이지")
    })
    @GetMapping
    public String admin(Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        return "admin";
    }
}
