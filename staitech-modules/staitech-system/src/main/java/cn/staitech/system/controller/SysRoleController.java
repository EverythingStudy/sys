package cn.staitech.system.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.exception.ServiceException;
import cn.staitech.common.core.utils.PageUtils;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.common.core.utils.poi.ExcelUtil;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.system.api.domain.SysRole;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.domain.SysUserRole;
import cn.staitech.system.domain.vo.RoleInfoOut;
import cn.staitech.system.domain.vo.SysRoleInsertVO;
import cn.staitech.system.domain.vo.SysRoleQueryVO;
import cn.staitech.system.domain.vo.SysRoleUpdateVO;
import cn.staitech.system.service.ISysRoleService;
import cn.staitech.system.service.ISysUserService;
import cn.staitech.system.utils.MessageSource;
import cn.staitech.system.utils.PageMaster;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 角色信息
 *
 * @author staitech
 */
@Api(value = "角色管理", tags = "角色管理")
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController {
    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysUserService userService;


    @ApiOperation(value = "角色列表(不分页)", notes = "gjt", response = SysRole.class)
    //@RequiresPermissions("system:role:query")
    @GetMapping("/roleList")
    public R<List<SysRole>> list() {
        SysRoleQueryVO role = new SysRoleQueryVO();
        List<SysRole> list = roleService.selectRoleList(role);
        return R.ok(list, MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 角色列表
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "角色列表", notes = "YangLei", response = SysRole.class)
    @RequiresPermissions("system:role:query")
    @PostMapping("/list")
    public R<PageMaster<SysRole>> list(@Validated @RequestBody SysRoleQueryVO role) {
        PageUtils.startPage(role.getPageNum(), role.getPageSize());
        List<SysRole> list = roleService.selectRoleList(role);
        PageMaster<SysRole> pageMaster = new PageMaster<>(list);
        return R.ok(pageMaster, MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "新增角色", notes = "YangLei", response = SysRole.class)
    @RequiresPermissions("system:role:add")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public R<Integer> add(@Validated @RequestBody SysRoleInsertVO role) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(role, sysRole);
        if (!roleService.checkRoleNameUnique(sysRole)) {
            throw new ServiceException(MessageSource.M("SYS_ROLE_HAD"));
        }
        if (!roleService.checkRoleKeyUnique(sysRole)) {
            throw new ServiceException(MessageSource.M("SYS_ROLE_CODE_HAD"));
        }
        return R.ok(roleService.insertRole(sysRole), MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "修改角色", notes = "YangLei", response = SysRole.class)
    @RequiresPermissions("system:role:edit")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public R<Integer> edit(@Validated @RequestBody SysRoleUpdateVO role) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(role, sysRole);
        SysRole roleById = roleService.selectRoleById(sysRole.getRoleId());
        if (ObjectUtil.isNull(roleById)) {
            throw new ServiceException(MessageSource.M("UPDATE_SYS_ROLE").concat("'").concat(role.getRoleName()).concat("'").concat(MessageSource.M("ERROR_NO_DATA")));
        }
        if (!roleService.checkRoleNameUnique(sysRole)) {
            throw new ServiceException(MessageSource.M("UPDATE_SYS_ROLE").concat("'").concat(role.getRoleName()).concat("'").concat(MessageSource.M("SYS_ROLE_ERROR_HAD")));
        }
        if (!roleService.checkRoleKeyUnique(sysRole)) {
            throw new ServiceException(MessageSource.M("UPDATE_SYS_ROLE").concat("'").concat(role.getRoleName()).concat("'").concat(MessageSource.M("SYS_ROLE_ERROR_CODE_HAD")));
        }
        return R.ok(roleService.updateRole(sysRole), MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "修改角色状态", notes = "YangLei", response = SysRole.class)
    @RequiresPermissions("system:role:status")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@ApiParam(name = "roleId", value = "角色ID") @RequestParam("roleId") Long roleId) {
        return R.ok(roleService.updateRoleStatus(roleId), MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "YangLei")
    @RequiresPermissions("system:role:remove")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/del/{roleId}")
    public R<Integer> remove(@ApiParam(name = "roleId", value = "角色ID") @PathVariable Long roleId) {
        return R.ok(roleService.deleteRoleById(roleId), MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 获取角色选择框列表
     *
     * @return
     */
    @ApiOperation(value = "获取角色选择框列表", notes = "YangLei")
    // @RequiresPermissions("system:role:query")
    @GetMapping("/optionselect")
    public R<List<SysRole>> optionselect() {
        return R.ok(roleService.optionselect(), MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 根据角色ID获取详细信息
     *
     * @param roleId
     * @return
     */
    /*@ApiOperation(value = "根据角色ID获取详细信息", notes = "YangLei", response = SysRole.class)
    @RequiresPermissions("system:role:query")
    @GetMapping(value = "/{roleId}")
    public R<SysRole> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return R.ok(roleService.selectRoleById(roleId), MessageSource.M("OPERATE_SUCCEED"));
    }*/
    /**
     * 根据角色ID获取详细信息
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据角色ID获取详细信息")
    @RequiresPermissions("system:role:query")
    @GetMapping(value = "/{roleId}")
    public R<RoleInfoOut> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return roleService.getInfoById(roleId);

    }

    /**
     * 角色导出Excel
     *
     * @param response
     * @param role
     */
    @ApiIgnore
    @ApiOperation(value = "角色导出Excel", response = SysRole.class)
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.EXPORT)
    //@RequiresPermissions("system:role:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysRoleQueryVO role) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        util.exportExcel(response, list, MessageSource.M("SYS_ROLE_DATA"));
    }

    /**
     * 修改保存数据权限
     *
     * @param role
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "修改保存数据权限", response = SysRole.class)
    //@RequiresPermissions("system:role:edit")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return toAjax(roleService.authDataScope(role));
    }

    /**
     * 查询已分配用户角色列表
     *
     * @param user
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "查询已分配用户角色列表", response = SysUser.class)
    // @RequiresPermissions("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo allocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectAllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 查询未分配用户角色列表
     *
     * @param user
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "查询未分配用户角色列表", response = SysUser.class)
    // @RequiresPermissions("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo unallocatedList(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权用户
     *
     * @param userRole
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "取消授权用户")
    // @RequiresPermissions("system:role:edit")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public AjaxResult cancelAuthUser(@RequestBody SysUserRole userRole) {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "批量选择用户授权")
    // @RequiresPermissions("system:role:edit")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(Long roleId, Long[] userIds) {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "批量选择用户授权")
    //@RequiresPermissions("system:role:edit")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(Long roleId, Long[] userIds) {
        roleService.checkRoleDataScope(roleId);
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }
}