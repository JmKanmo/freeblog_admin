package com.service.freeblog_admin.web.model.admin;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Builder
@Data
public class AdminUserSignupInput {
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[a-z]{1}[a-z0-9]{4,11}$", message = "아이디 패턴에 어긋나는 형식입니다.")
    private final String id;

    @Email
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 패턴에 어긋나는 형식입니다.")
    private final String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "비밀번호 패턴에 어긋나는 형식입니다.")
    private final String password;

    @NotEmpty(message = "닉네임이 비어있습니다.")
    @NotBlank(message = "닉네임은 공백만 올 수 없습니다")
    @Size(max = 20, message = "닉네임은 최대 20글자 까지 작성 가능합니다.")
    private final String nickname;

    private final boolean emailCheckFlag;

    private final boolean idCheckFlag;
}
