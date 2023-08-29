package com.service.freeblog_admin.web.model.music;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class MusicCategoryUpdateInput {
    private final Long updateId;

    @NotEmpty(message = "뮤직 카테고리 이름이 비어있습니다.")
    @NotBlank(message = "뮤직 카테고리 이름은 공백만 올 수 없습니다.")
    @Size(max = 50, message = "뮤직 카테고리 이름은 최대 50글자 까지 작성 가능합니다.")
    private final String updateName;
}
