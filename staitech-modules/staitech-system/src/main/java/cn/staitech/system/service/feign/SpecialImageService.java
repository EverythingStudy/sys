package cn.staitech.system.service.feign;

import cn.staitech.common.core.constant.ServiceNameConstants;
import cn.staitech.system.domain.SpecialImage;
import cn.staitech.system.domain.SpecialRoleUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "SpecialImageService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface SpecialImageService {

    /**
     * 查询用户所参与的专题
     * @param userId 用户id
     * @return
     */
    @GetMapping("/specialImage/selectSpecialId")
    List<SpecialImage> selectSpecialId(@RequestParam("specialId") Long specialId);



}
