package cn.staitech.system.controller;

import cn.staitech.common.core.utils.poi.ExcelUtil;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.annotation.InnerAuth;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.system.api.domain.SysOperLog;
import cn.staitech.system.api.domain.document.SysOperLogDoc;
import cn.staitech.system.service.ILogService;
import cn.staitech.system.service.ISysOperLogService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 操作日志记录
 *
 * @author staitech
 */
@Api(value = "操作日志记录", tags = "操作日志记录")
@RestController
@RequestMapping("/operlog")
public class SysOperlogController extends BaseController {
    @Autowired
    private ISysOperLogService operLogService;

    @Autowired
    private ILogService logService;

    @RequiresPermissions("system:log:operation")
    @PostMapping("/queryOperLogByPage/es/v01")
    public TableDataInfo queryOperLogByPage(@RequestBody Map params) throws Exception {
        return logService.queryOperLogByPage(params);
    }


    @ApiOperation(value = "操作日志导出", response = SysOperLogDoc.class)
    @Log(title = "系统管理", menu = "系统管理", subMenu = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:log:exportoperation")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Map params) throws Exception {
        List<SysOperLogDoc> list = logService.queryOperLog(params);
        ExcelUtil<SysOperLogDoc> util = new ExcelUtil<>(SysOperLogDoc.class);
        util.exportExcel(response, list, MessageSource.M("OPER_LOG"));
    }

    @Log(title = "系统管理", menu = "系统管理", subMenu = "操作日志", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:operlog:remove")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @RequiresPermissions("system:operlog:remove")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return AjaxResult.success(MessageSource.M("OPERATE_SUCCEED"));
    }

    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody SysOperLog operLog) {
        return toAjax(operLogService.insertOperlog(operLog));
    }
}
