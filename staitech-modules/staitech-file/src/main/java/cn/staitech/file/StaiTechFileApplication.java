package cn.staitech.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import cn.staitech.common.swagger.annotation.EnableCustomSwagger2;

import java.util.TimeZone;

/**
 * 文件服务
 * 
 * @author staitech
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class StaiTechFileApplication
{
    public static void main(String[] args)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(cn.staitech.file.StaiTechFileApplication.class, args);
        System.out.println("文件服务模块启动成功 ");
    }
}