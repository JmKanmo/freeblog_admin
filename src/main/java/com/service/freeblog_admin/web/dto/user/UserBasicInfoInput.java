package com.service.freeblog_admin.web.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserBasicInfoInput {
    @NotEmpty(message = "닉네임이 비어있습니다.")
    @NotBlank(message = "닉네임은 공백만 올 수 없습니다")
    @Size(max = 20, message = "닉네임은 최대 20글자 까지 작성 가능합니다.")
    private final String nickname;
}
