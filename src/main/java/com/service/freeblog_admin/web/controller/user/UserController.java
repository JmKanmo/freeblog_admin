package com.service.freeblog_admin.web.controller.user;

import com.service.freeblog_admin.util.BlogAdminUtil;
import com.service.freeblog_admin.web.domain.admin.AdminUser;
import com.service.freeblog_admin.web.dto.admin.AdminUserDto;
import com.service.freeblog_admin.web.dto.user.UserBasicInfoInput;
import com.service.freeblog_admin.web.dto.user.UserPasswordInput;
import com.service.freeblog_admin.web.dto.user.UserWithdrawInput;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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
        if (!BlogAdminUtil.isAuth(authentication)) {
            return BlogAdminUtil.redirect("/user/login");
        }
        model.addAttribute("userWithdrawInput", UserWithdrawInput.builder().build());
        return "user/withdraw";
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 진행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 완료"),
            @ApiResponse(responseCode = "500", description = "데이터베이스 작업 실패 등의 오류로 회원 탈퇴 실패")
    })
    @DeleteMapping("/withdraw")
    public String withdraw(@Valid UserWithdrawInput userWithdrawInput, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest, Authentication authentication) {
        try {
            if (bindingResult.hasErrors()) {
                return "user/withdraw";
            } else if (authentication == null || authentication.getName() == null) {
                throw new AdminUserException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }
            adminUserService.withdraw(userWithdrawInput);
            model.addAttribute("result", "회원탈퇴 작업 완료");
            httpServletRequest.logout();
        } catch (UsernameNotFoundException | AdminUserException exception) {
            model.addAttribute("result", "회원탈퇴 작업 실패");
            model.addAttribute("error", exception.getMessage());
        } catch (Exception e) {
            model.addAttribute("result", "회원탈퇴 후 로그아웃 작업 실패");
            model.addAttribute("error", e.getMessage());
        }
        return "user/withdraw/withdraw-complete";
    }

    @Operation(summary = "비밀번호 변경 페이지", description = "비밀번호 변경 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 페이지")
    })
    @GetMapping("/update/password")
    public String updatePassword(HttpServletRequest request, Model model, Authentication authentication) {
        model.addAttribute("userPasswordInput", UserPasswordInput.builder().build());
        return "user/update/password";
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 진행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "500", description = "이메일 or 인증키 or 비밀번호 인증 오류로 비밀번호 변경 실패")
    })
    @PatchMapping("/update/password")
    public String updatePassword(@Valid UserPasswordInput userPasswordInput, BindingResult bindingResult, Model model, Authentication authentication, HttpServletRequest httpServletRequest) throws Exception {
        try {
            if (bindingResult.hasErrors()) {
                return "user/update/password";
            } else if (authentication == null || authentication.getName() == null) {
                throw new AdminUserException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }

            adminUserService.updatePassword(userPasswordInput);
            model.addAttribute("result", "비밀번호 변경 작업 완료");
            httpServletRequest.logout();
        } catch (UsernameNotFoundException | AdminUserException exception) {
            model.addAttribute("result", "비밀번호 변경 작업 실패");
            model.addAttribute("error", String.format("비밀번호 변경에 실패하였습니다. %s", exception.getMessage()));
        }
        return "user/update/update-password-complete";
    }

    @Operation(summary = "기본정보 변경 페이지", description = "기본정보 변경 페이지 반환 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기본정보 변경 페이지")
    })
    @GetMapping("/update/basic-info")
    public String updateBasicInfo(HttpServletRequest request, Model model, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new AdminUserException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
        }
        model.addAttribute("userBasicInfoInput", UserBasicInfoInput.builder().build());
        return "user/update/basic-info";
    }

    @Operation(summary = "기본 정보 수정 작업 진행", description = "기본 정보 수정 작업 진행 메서드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기본 정보 수정 작업 성공 페이지"),
            @ApiResponse(responseCode = "500", description = "기본 정보 수정 실패 페이지")
    })
    @PatchMapping("/update/basic-info")
    public String basicInfoUpdate(@Valid UserBasicInfoInput userBasicInfoInput, BindingResult bindingResult, Principal principal, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "user/update/basic-info";
            } else if (principal == null || principal.getName() == null) {
                throw new AdminUserException(ServiceExceptionMessage.NOT_LOGIN_STATUS_ACCESS.message());
            }

            adminUserService.updateUserBasicInfo(userBasicInfoInput, principal.getName());
            model.addAttribute("result", "기본 정보 수정 작업 완료. 페이지를 새로고침 후, 변경 사항 확인 가능합니다.");
        } catch (UsernameNotFoundException exception) {
            model.addAttribute("result", "기본 정보 수정 작업 실패");
            model.addAttribute("error", exception.getMessage());
        }
        return "user/update/update-info-complete";
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
