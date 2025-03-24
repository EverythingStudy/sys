package cn.staitech.system.controller;


import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.system.api.domain.SysProjectRole;
import cn.staitech.system.domain.vo.ProjectRoleInsertVO;
import cn.staitech.system.service.ISysProjectRoleService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 项目角色信息
 *
 * @author wangfeng
 * @date 2023/03/25 14:35
 */
@Api(value = "项目角色信息", tags = "项目角色信息")
@RestController
@RequestMapping("/projectRole")
public class SysProjectRoleController extends BaseController {

    @Resource
    ISysProjectRoleService projectRoleService;

    /**
     * 新增角色
     */
    @ApiOperation(value = "修改保存项目角色", response = SysProjectRole.class)
    // @RequiresPermissions("system:projectrole:add")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "增加角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<SysProjectRole> add(@Validated @RequestBody ProjectRoleInsertVO role) {
        // 查询参数
        SysProjectRole projectRole = new SysProjectRole();
        BeanUtils.copyProperties(role, projectRole);

        // 校验该项目是否有角色
        Integer roleCount = projectRoleService.countProjectRole(projectRole);

        if (roleCount > 0) {
            return R.fail(MessageSource.M("ADD_PROJECT_ROLE_ERROR"));
        }

        // 插入
        projectRoleService.insertProjectRole(projectRole);
        return R.ok(projectRole);
    }


    @ApiOperation(value = "项目角色列表", response = SysProjectRole.class)
    //@RequiresPermissions("system:projectrole:list")
    @GetMapping("/list")
    public TableDataInfo list(SysProjectRole role) {
        startPage();
        List<SysProjectRole> list = projectRoleService.selectProjectRoleList(role);
        return getDataTable(list);
    }


}

