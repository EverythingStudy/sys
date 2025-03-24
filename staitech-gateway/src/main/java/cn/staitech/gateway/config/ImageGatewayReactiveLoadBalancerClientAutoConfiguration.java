package cn.staitech.gateway.config;

import cn.staitech.gateway.filter.ImageReactiveLoadBalancerClientFilter;
import cn.staitech.gateway.loadbalancer.ImageLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: WANG Feng
 * @Date: 2023/06/28 15:38
 * @Description: 自定义LoadBalancer配置类
 */
@Configuration
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ImageGatewayReactiveLoadBalancerClientAutoConfiguration {
    @Bean
    public ImageLoadBalancer getImageLoadBalancer(DiscoveryClient discoveryClient) {
        return new ImageLoadBalancer(discoveryClient);
    }

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(ImageLoadBalancer imageLoadBalancer, GatewayLoadBalancerProperties properties) {
        return new ImageReactiveLoadBalancerClientFilter(properties, imageLoadBalancer);
    }


}
