package cn.staitech.system.controller;

import cn.staitech.common.core.domain.PageResponse;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.annotation.InnerAuth;
import cn.staitech.common.security.annotation.Logical;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.api.model.LoginUser;
import cn.staitech.system.domain.SysOrganizationAuthorization;
import cn.staitech.system.domain.user.in.*;
import cn.staitech.system.domain.user.out.OrganizationListQueryOut;
import cn.staitech.system.domain.user.out.UserQeuryOut;
import cn.staitech.system.domain.user.out.data.UserInfoGetOut;
import cn.staitech.system.domain.vo.organization.OrganizationSelectResVo;
import cn.staitech.system.service.ISysPermissionService;
import cn.staitech.system.service.ISysUserService;
import cn.staitech.system.service.SysOrganizationService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 * @author staitech
 */
@Slf4j
@Api(value = "用户信息2.0", tags = "用户信息")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPermissionService permissionService;


    @Resource
    private SysOrganizationService organizationService;

    /**
     * 获取用户列表
     *
     * @param req
     * @return 用户列表
     */
    @ApiOperation(value = "用户列表接口-分页")
    // @RequiresPermissions(value = {"system:user:query", "projectConfig:users:list"}, logical = Logical.OR)
    @PostMapping("/list")
    public R<PageResponse<SysUser>> list(@RequestBody @Validated UserListQeuryIn req) {
        PageResponse<SysUser> resp = userService.selectUserListExt(req);
        return R.ok(resp);
    }


    /**
     * 获取当前用户信息
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "获取当前用户信息", response = LoginUser.class)
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username) {
        SysUser sysUser = userService.selectUserByUserNameExt(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户不存在!");
            //return R.fail(MessageSource.M("NO_USER"));
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());

        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        loginUser.setRoles(roles);
        loginUser.setPermissions(permissions);
        return R.ok(loginUser);
    }


    @ApiOperation(value = "根据机构id查询详情信息")
    @InnerAuth
    @GetMapping("/selectByOrganizationId")
    public R<SysOrganizationAuthorization> selectByOrganizationId(@RequestParam(name = "organizationId", value = "organizationId", required = true) String organizationId) {
        return R.ok(organizationService.selectOrganizationAuthorizationPrimaryKey(Long.valueOf(organizationId)));

    }


    @ApiOperation(value = "根据机构id查询机构信息")
    @InnerAuth
    @GetMapping("/getOrganizationById")
    public R<OrganizationSelectResVo> getOrganizationById(@RequestParam(name = "organizationId", value = "organizationId", required = true) String organizationId) {
        return R.ok(organizationService.selectPrimaryKey(Long.valueOf(organizationId)));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息")
//    @RequiresPermissions("system:user:query")
    @GetMapping("/getInfo")
    public R<UserInfoGetOut> getInfo() {
        UserInfoGetOut resp = userService.getInfo();
        return R.ok(resp);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation(value = "根据用户编号获取详细信息:修改前查询")
    //@RequiresPermissions("system:user:query")
    @GetMapping(value = {"{userId}"})
    public R<UserQeuryOut> getInfo(@PathVariable(value = "userId", required = false) Long userId) {

        UserQeuryOut resp = new UserQeuryOut();
        if (StringUtils.isNotNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            List<Long> longs = userService.selectRoleByUser(userId);
            sysUser.setRoleIds(longs.stream().toArray(Long[]::new));
            resp.setUserInfo(sysUser);
        }
        return R.ok(resp);
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "用户管理：新增用户")
    @RequiresPermissions("system:user:add")
    @Log(title = "新增", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@Validated @RequestBody UserAddIn user) throws Exception {
        return userService.insertUserExt(user);
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
    @RequiresPermissions("system:user:edit")
    @Log(title = "修改", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@Validated @RequestBody UserUpdateIn req) {
        return userService.updateUserExt(req);
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "用户删除")
    @RequiresPermissions("system:user:remove")
    @Log(title = "删除", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, SecurityUtils.getUserId())) {
            return R.fail(MessageSource.M("USER_DELETE_ERROR"));
        }
        return R.ok(userService.deleteUserByIds(userIds));
    }


    /**
     * 重置密码
     */
    @ApiOperationSupport(author = "wudi")
    @ApiOperation(value = "重置密码(修改账号密码状态)")
    @RequiresPermissions("system:user:resetpwd")
    @Log(title = "重置密码", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody ResetPwdIn req) {
        try {
            return userService.resetPwd(req.getUserId());
        } catch (Exception e) {
            return R.fail();
        }
    }

    /**
     * 状态修改
     */
    @ApiOperation(value = "状态修改2.0", notes = "状态修改-wudi")
    @RequiresPermissions("system:user:status")
    @Log(title = "状态修改", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody ChangeStatusIn req) {
        return R.ok(userService.updateUserStatusExt(req));
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码2.0", notes = "密码修改-wudi")
    //@RequiresPermissions("system:user:resetpwd")
    @Log(title = "修改密码", menu = "系统管理", subMenu = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R updatePwd(@RequestBody @Validated PassWordUpdateIn req) {
        return userService.updatePwd(req);
    }

    @ApiOperation(value = "机构下拉框2.0", notes = "机构下拉框-wudi")
    @GetMapping("/getOrganizationList")
    public R<List<OrganizationListQueryOut>> getOrganizationList() {
        List<OrganizationListQueryOut> resp = userService.selectOrganizationList();
        return R.ok(resp);

    }
}
