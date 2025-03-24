package cn.staitech.system.service.feign;

import cn.staitech.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "SubImageService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface SubImageService {

    /**
     * 查询用户所参与的专题
     */
    @GetMapping("/specialRoleUser/selectUserId")
    Integer selectSpecialCount(@RequestParam("specialImageId") Long specialImageId, @RequestParam("sliceBatchNumber") Long sliceBatchNumber);
}
