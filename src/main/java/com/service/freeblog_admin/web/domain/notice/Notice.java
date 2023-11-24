package com.service.freeblog_admin.web.domain.notice;

import com.service.freeblog_admin.util.ConstUtil;
import com.service.freeblog_admin.util.domain.BaseTimeEntity;
import com.service.freeblog_admin.web.model.notice.NoticeInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;

    @Lob
    @Length(max = ConstUtil.MAX_NOTICE_CONTENT_SIZE)
    private String contents;

    private String summary;

    private String uploadKey;

    public static Notice from(NoticeInput noticeInput) {
        return Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .summary(noticeInput.getSummary())
                .uploadKey(noticeInput.getUploadKey())
                .isBaseTimezone(true)
                .build();
    }
}
