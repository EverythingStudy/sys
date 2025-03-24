package cn.staitech.gateway.loadbalancer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.staitech.gateway.config.properties.SlideServerProperties;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

/**
 * @Author: WANG Feng
 * @Date: 2023/06/28 15:38
 * @Description: 负载均衡策略的实现
 */
@AllArgsConstructor
public class ImageLoadBalancer {
    @Autowired
    private SlideServerProperties slideServerProperties;

    @Value("${intelligentAnno:1}")
    private String intelligentAnno;
    @Value("${intelligentEvaluation:1}")
    private String intelligentEvaluation;
    private static final Logger log = LoggerFactory.getLogger(ImageLoadBalancer.class);
    private DiscoveryClient discoveryClient;

    public ImageLoadBalancer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 根据serviceId 筛选可用服务
     *
     * @param serviceId 服务ID
     * @param request   当前请求
     * @return
     */
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        //注册中心无实例 抛出异常
        if (CollUtil.isEmpty(instances)) {
            log.warn("+++No instance available for {}", serviceId);
            throw new NotFoundException("No instance available for " + serviceId);
        }

        //注册中心只有1个实例，直接返回
        if (instances.size() == 1) {
            return instances.get(0);
        }

//        for (ServiceInstance instance : instances) {
//            log.info("serviceId:{} ,instance size：{} ,instace:{}", serviceId, instances.size(), JSON.toJSONString(instance));
//        }
//        log.info("---------request:serviceId:{}  ,uri:{}  ,rawpath:{}  ,host{}  ,port:{}", serviceId, request.getURI(), request.getURI().getRawPath(), request.getURI().getHost(), request.getURI().getPort());

        String rawPath = request.getURI().getRawPath();

        /**
         * 20230915 需求为智能标注与智能评审负载分离，支持场景为两节点
         */
        if (serviceId.equals("staitech-anno")&&instances.size()>1) {
            if (rawPath.indexOf("intelligentEvaluation")>0) {
                for (ServiceInstance instance : instances) {
                    log.info("--------------uri:{}  ,host:{}  ,port:{}", instance.getUri(), instance.getHost(), instance.getPort());
                    if (intelligentEvaluation.equals(instance.getHost())) {
                        return instance;
                    }
                }
            }
            if (rawPath.indexOf("intelligentAnno")>0) {
                for (ServiceInstance instance : instances) {
                    log.info("--------------uri:{}  ,host:{}  ,port:{}", instance.getUri(), instance.getHost(), instance.getPort());
                    if (intelligentAnno.equals(instance.getHost())) {
                        return instance;
                    }
                }
            }
        }

        if (serviceId.equals("staitech-openslide")&&instances.size()>1) {
            if (request.getPath().value().equals("/bigPicture/uploadSlice")){
                log.info("网关处理大图像上传,url：[{}]",request.getURI());
                try {
                    String param = request.getURI().getQuery().split("&")[1];
                    String imageId = param.split("=")[1];
                    log.info("网关处理大图像上传,图像imageId：[{}]",imageId);
                    int serviceIndex = Hashing.consistentHash(imageId.hashCode(),instances.size());
                    log.info("网关处理大图像上传,staitech-openslide在线服务数量:[{}],Hashing.consistentHash一致性hash值：[{}]",instances.size(),serviceIndex);
                    ServiceInstance serviceInstance = instances.get(serviceIndex);
                    log.info("网关处理大图像上传,选择服务实例：[{}]",serviceInstance.getHost());
                    return serviceInstance;
                }catch (Exception e){
                    log.error("网关处理大图像上传,服务路由异常:[{}]",e.getMessage());
                    return instances.get(0);
                }
            }
        }
        return instances.get(RandomUtil.randomInt(instances.size()));
    }

}