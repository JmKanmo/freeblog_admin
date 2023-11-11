package com.service.freeblog_admin;

import com.service.freeblog_admin.util.ConstUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class FreeblogAdminApplication {
    @PostConstruct
    public void setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ConstUtil.DEFAULT_SERVER_TIMEZONE));
    }

    public static void main(String[] args) {
        SpringApplication.run(FreeblogAdminApplication.class, args);
    }
}
