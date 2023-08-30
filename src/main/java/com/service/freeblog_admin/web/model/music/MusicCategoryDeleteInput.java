package com.service.freeblog_admin.web.model.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicCategoryDeleteInput {
    private final Long deleteId;
}
