package cn.staitech.system.service;

import cn.staitech.common.core.constant.ServiceNameConstants;
import cn.staitech.system.domain.ProjectMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 日志服务
 *
 * @author staitech
 */
@FeignClient(contextId = "ProjectMenuService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface ProjectMenuService {

    @GetMapping("/projectMenu/byUserId")
    List<ProjectMember> byUserId(@RequestParam("userId") Long userId);
}
