package cn.staitech.gateway;

import cn.staitech.gateway.util.MessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

/**
 * 网关启动程序
 * 
 * @author staitech
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class StaiTechGatewayApplication
{
    public StaiTechGatewayApplication(org.springframework.context.MessageSource messageSource) {
        MessageSource.init(messageSource);
    }
    public static void main(String[] args)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(cn.staitech.gateway.StaiTechGatewayApplication.class, args);
        System.out.println("生仝病理图片处理系统网关启动成功");
    }
}
