package com.service.freeblog_admin.web.controller.user;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.admin.AdminUser;
import com.service.freeblog_admin.web.dto.admin.AdminUserDto;
import com.service.freeblog_admin.web.error.constants.ServiceExceptionMessage;
import com.service.freeblog_admin.web.error.model.admin.AdminUserException;
import com.service.freeblog_admin.web.model.admin.AdminUserSignupInput;
import com.service.freeblog_admin.web.service.admin.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Tag(name = "사용자", description = "사용자 관련 API")
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final AdminUserService adminUserService;

    @Operation(summary = "회원탈퇴 페이지", description = "회원탈퇴 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 페이지")
    })
    @GetMapping("/withdraw")
    public String withdraw(HttpServletRequest request, Model model, Authentication authentication) {
        return "user/withdraw";
    }

    @Operation(summary = "비밀번호 변경 페이지", description = "비밀번호 변경 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 페이지")
    })
    @GetMapping("/update/password")
    public String updatePassword(HttpServletRequest request, Model model, Authentication authentication) {
        return "user/update/password";
    }

    @Operation(summary = "기본정보 변경 페이지", description = "기본정보 변경 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기본정보 변경 페이지")
    })
    @GetMapping("/update/basic-info")
    public String updateBasicInfo(HttpServletRequest request, Model model, Authentication authentication) {
        return "user/update/basic-info";
    }

    @Operation(summary = "설정 페이지", description = "설정 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설정 페이지")
    })
    @GetMapping("/settings")
    public String setting(HttpServletRequest request, Model model, Authentication authentication) {
        AdminUserDto adminUserDto = adminUserService.findAdminUserDto(authentication.getName());
        model.addAttribute("user_setting", adminUserDto);
        return "user/settings";
    }

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

    @Operation(summary = "회원가입 페이지", description = "회원가입 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 페이지")
    })
    @GetMapping("/signup")
    public String signup(Model model, Authentication authentication) {
        if (BlogAdminUtil.isAuth(authentication)) {
            return BlogAdminUtil.redirect("/");
        }
        model.addAttribute("adminUserSignUpInput", AdminUserSignupInput.builder().build());
        return "user/signup";
    }

    @Operation(summary = "회원가입", description = "회원가입 진행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "500", description = "이메일 or id 인증 오류, 중복 가입 시도, 데이터베이스 오류 등으로 회원가입 실패")
    })
    @PostMapping("/signup")
    public String signUp(@Valid AdminUserSignupInput adminSignupForm, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "user/signup";
            } else if (!adminSignupForm.isEmailCheckFlag() || !adminSignupForm.isIdCheckFlag()) {
                List<String> stringList = new LinkedList<>();

                if (!adminSignupForm.isEmailCheckFlag()) {
                    stringList.add(ServiceExceptionMessage.NOT_CHECKED_EMAIL.message());
                }

                if (!adminSignupForm.isIdCheckFlag()) {
                    stringList.add(ServiceExceptionMessage.NOT_CHECKED_ID.message());
                }
                throw new AdminUserException(String.join(",", stringList));
            }

            if (!adminUserService.checkSecretKey(adminSignupForm.getSecretKey())) {
                throw new AdminUserException(ServiceExceptionMessage.SECRET_KEY_MISMATCH.message());
            }
            adminUserService.signUp(AdminUser.from(adminSignupForm));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error/signup-fail";
        }
        return "user/signup/signup-complete";
    }
}
