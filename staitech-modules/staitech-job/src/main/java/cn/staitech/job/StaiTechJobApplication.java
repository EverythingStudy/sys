package cn.staitech.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.staitech.common.security.annotation.EnableCustomConfig;
import cn.staitech.common.security.annotation.EnableRyFeignClients;
import cn.staitech.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.TimeZone;

/**
 * 定时任务
 * 
 * @author staitech
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
@MapperScan({"cn.staitech.job.mapper","cn.staitech.common.log.repository"})
@EnableElasticsearchRepositories(basePackages = {"cn.staitech.common.log.elasticsearchRepositories","cn.staitech.job.elasticsearchRepositories"})

public class StaiTechJobApplication
{
    public static void main(String[] args)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(cn.staitech.job.StaiTechJobApplication.class, args);
        System.out.println("定时任务模块启动成功");
    }
}
