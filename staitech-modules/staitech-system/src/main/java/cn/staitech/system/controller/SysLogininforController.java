package cn.staitech.system.controller;

import cn.staitech.common.core.utils.poi.ExcelUtil;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.annotation.InnerAuth;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.system.api.domain.SysLogininfor;
import cn.staitech.system.api.domain.document.SysLoginInfoDoc;
import cn.staitech.system.service.ILogService;
import cn.staitech.system.service.ISysLogininforService;
import cn.staitech.system.service.feign.SpecialRoleService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 系统访问记录
 *
 * @author staitech
 */
@Slf4j
@Api(value = "系统访问记录", tags = "系统访问记录")
@RestController
@RequestMapping("/logininfor")
public class SysLogininforController extends BaseController {
    @Autowired
    private ISysLogininforService logininforService;
    @Autowired
    private ILogService logService;

    @Resource
    private SpecialRoleService specialRoleService;

    @ApiOperation(value = "系统访问记录接口", response = SysLoginInfoDoc.class)
    //@RequiresPermissions("system:log:login")
    @PostMapping("/queryLoginLogByPage/es/v01")
    public TableDataInfo queryLoginLogByPage(@RequestBody Map params) throws Exception {
        return logService.queryLoginLogByPage(params);
    }

    @ApiOperation(value = "登录日志导出", response = SysLoginInfoDoc.class)
    @Log(title = "登录日志导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:log:exportLogin")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Map params) throws Exception {
        List<SysLoginInfoDoc> list = logService.queryLoginLog(params);
        ExcelUtil<SysLoginInfoDoc> util = new ExcelUtil<>(SysLoginInfoDoc.class);
        util.exportExcel(response, list, MessageSource.M("LOGIN_LOG"));
    }


    @ApiOperation(value = "登录日志-删除", response = SysLogininfor.class)
    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @ApiOperation(value = "登录日志", response = SysLogininfor.class)
    @RequiresPermissions("system:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return AjaxResult.success(MessageSource.M("OPERATE_SUCCEED"));
    }

    @ApiOperation(value = "登录日志-添加", response = SysLogininfor.class)
    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody SysLogininfor logininfor) {
        specialRoleService.logOutSpecial(logininfor.getUserName());
        return toAjax(logininforService.insertLogininfor(logininfor));
    }
}
