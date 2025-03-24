package cn.staitech.system.service.feign;

import cn.staitech.common.core.constant.ServiceNameConstants;
import cn.staitech.system.api.domain.SpecialRole;
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
@FeignClient(contextId = "SpecialRoleService", value = ServiceNameConstants.SYSTEM_ANNO)
public interface SpecialRoleService {

    /**
     * 根据用户id查询专题角色列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/specialRole/userId")
    List<SpecialRole> querySpecialRoleListByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据角色id查询专题权限列表
     *
     * @param roleId
     * @return
     */
    @GetMapping("/specialMenu/roleId")
    List<SpecialRole> querySpecialRolePermsByRoleId(@RequestParam("roleId") Long roleId);
    
    @GetMapping("/specialSlice/logOut")
    void logOutSpecial(@RequestParam("userName") String userName);
}
