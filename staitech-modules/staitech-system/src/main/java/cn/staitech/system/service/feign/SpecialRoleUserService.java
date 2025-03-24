package cn.staitech.system.service.feign;

import cn.staitech.common.core.constant.ServiceNameConstants;
import cn.staitech.system.domain.SpecialRoleUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@FeignClient(contextId = "SpecialRoleUserService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface SpecialRoleUserService {

    /**
     * 查询用户所参与的专题
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/specialRoleUser/selectUserId")
    List<SpecialRoleUser> selectUserId(@RequestParam("userId") Long userId);

}
