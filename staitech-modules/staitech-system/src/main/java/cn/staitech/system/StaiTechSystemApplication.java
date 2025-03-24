package cn.staitech.system;

import cn.staitech.common.security.annotation.EnableCustomConfig;
import cn.staitech.common.security.annotation.EnableRyFeignClients;
import cn.staitech.common.swagger.annotation.EnableCustomSwagger2;
import cn.staitech.system.utils.MessageSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.TimeZone;

/**
 * 系统模块
 *
 * @author staitech
 */
@EnableWebSocket
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"cn.staitech.common.log.elasticsearchRepositories"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.staitech.system.mapper")
public class StaiTechSystemApplication {
    public StaiTechSystemApplication(org.springframework.context.MessageSource messageSource) {
        MessageSource.init(messageSource);
    }

    public static void main(String[] args) {
        //jvm参数设置时间 -Duser.timezone="Asia/Shanghai"
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(cn.staitech.system.StaiTechSystemApplication.class, args);
        System.out.println("系统模块启动成功");
    }

}
