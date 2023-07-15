package com.service.freeblog_admin;

import com.service.freeblog_admin.web.dto.notice.NoticeDto;
import com.service.freeblog_admin.web.dto.notice.NoticeSearchPagingDto;
import com.service.freeblog_admin.web.repository.notice.NoticeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MapperTest {
    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    @Test
    void contextLoads() {
        List<NoticeDto> noticeDtoList = noticeMapper.findNoticeDtoListByPaging(new NoticeSearchPagingDto());
        Assertions.assertNotNull(noticeDtoList);
    }

    @Transactional(readOnly = true)
    @Test
    void searchNotice() {
        int count = noticeMapper.findNoticeCountByKeyword(null, null);
        System.out.println(count);
    }
}
