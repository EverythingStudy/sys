package cn.staitech.system.controller;

import cn.staitech.common.core.constant.CacheConstants;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.redis.service.RedisService;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.system.api.model.LoginUser;
import cn.staitech.system.domain.SysUserOnline;
import cn.staitech.system.service.ISysUserOnlineService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author staitech
 */
@Api(value = "在线用户监控", tags = "在线用户监控")
@RestController
@RequestMapping("/online")
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisService redisService;

    private static final Logger log = LoggerFactory.getLogger(SysUserOnlineController.class);

    @ApiOperation(value = "在线用户列表接口", response = SysUserOnline.class)
    @RequiresPermissions("monitor:online:list")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = redisService.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @ApiOperation(value = "在线用户列表接口", response = SysUserOnline.class)
    @RequiresPermissions("monitor:online:forceLogout")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        log.error("SysUserOnlineController delete redis user token:{}", CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success(MessageSource.M("OPERATE_SUCCEED"));
    }
}
