package cn.staitech.auth;

import cn.staitech.auth.util.MessageSource;
import cn.staitech.common.security.annotation.EnableRyFeignClients;
import cn.staitech.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;

/**
 * 认证授权中心
 *
 * @author staitech
 */
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StaiTechAuthApplication {
    public StaiTechAuthApplication(org.springframework.context.MessageSource messageSource) {
        MessageSource.init(messageSource);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(cn.staitech.auth.StaiTechAuthApplication.class, args);
        System.out.println("认证授权中心启动成功");
    }
}
