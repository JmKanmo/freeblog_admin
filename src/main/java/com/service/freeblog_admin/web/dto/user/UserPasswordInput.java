package com.service.freeblog_admin.web.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserPasswordInput {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "비밀번호 패턴에 어긋나는 형식입니다.")
    private final String password;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "비밀번호 패턴에 어긋나는 형식입니다.")
    private final String rePassword;

    @NotEmpty(message = "인증키가 비어있습니다.")
    @NotBlank(message = "인증키는 공백만 올 수 없습니다.")
    private final String key;

    @Email
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 패턴에 어긋나는 형식입니다.")
    private final String email;
}
