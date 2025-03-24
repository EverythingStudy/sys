package cn.staitech.job.service.remote;

import cn.staitech.common.core.constant.ServiceNameConstants;
import cn.staitech.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author wudi
 * @Date 2023/8/10 9:53
 * @desc 远程接口调用消息发送
 */
@FeignClient(contextId = "ErisAlgorithmService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface ErisAlgorithmService {
    @GetMapping("/specialAnnotation/cutImage")
    R<String> cutImage(@RequestParam("specialImageId") String specialImageId);
}
