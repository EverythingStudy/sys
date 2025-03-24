package cn.staitech.system.controller;

import cn.staitech.common.core.constant.UserConstants;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.domain.SysMenu;
import cn.staitech.system.domain.vo.MenuTreeByUserIdOut;
import cn.staitech.system.domain.vo.RoleMenuTreeSelectOut;
import cn.staitech.system.domain.vo.RouterVo;
import cn.staitech.system.service.ISysMenuService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单信息
 *
 * @author staitech
 */
@Slf4j
@Api(value = "菜单信息", tags = "菜单信息")
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单列表
     *
     * @param menu
     * @return
     */
    //@RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表")
    public AjaxResult list(SysMenu menu) {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @ApiOperation(value = "根据菜单编号获取详细信息")
    //@RequiresPermissions("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     *
     * @param menu
     * @return
     */
    @ApiOperation(value = "获取菜单下拉树列表", notes = "YangLei")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu) {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param roleId
     * @return
     */

    @ApiOperation(value = "加载对应角色菜单列表树", notes = "YangLei")
    @GetMapping(value = "/roleMenuTreeselects/{roleId}")
    public AjaxResult roleMenuTreeSelects(@PathVariable("roleId") Long roleId) {
        List<Long> menuListByRoleId = menuService.selectMenuListByRoleId(roleId);
        ArrayList<SysMenu> menus = new ArrayList<>();
        for (Long menuId : menuListByRoleId) {
            menus.add(menuService.selectMenuById(menuId));
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 获取角色菜单列表树
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "获取角色菜单列表树", notes = "YangLei")
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public R<RoleMenuTreeSelectOut> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        Long userId = SecurityUtils.getUserId();
        // todo 用不用过滤当前用户权限
        List<SysMenu> menus = menuService.selectMenuList(userId);
        RoleMenuTreeSelectOut hashMap = new RoleMenuTreeSelectOut();

        hashMap.setCheckedKeys(menuService.selectMenuListByRoleId(roleId));

        hashMap.setMenus(menuService.buildMenuTreeSelect(menus));
        List<SysMenu> sysMenus = menuService.selectMenuTree(1L);
        List<SysMenu> collect = sysMenus.stream().filter(menu -> "0".equals(menu.getIsFunctionalModules())).collect(Collectors.toList());
        hashMap.setSysMenuList(collect);
        return R.ok(hashMap);
    }

    /**
     * 新增菜单
     */
    @ApiOperation(value = "新增菜单")
    // @RequiresPermissions("system:menu:add")
    // @Log(title = "系统管理", menu = "系统管理", subMenu = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))||UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error(MessageSource.M("ADD_MENU").concat(menu.getMenuName()).concat(MessageSource.M("ADD_UPDATE_MENU_ERROR")));
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error(MessageSource.M("ADD_MENU").concat(menu.getMenuName()).concat(MessageSource.M("ADD_UPDATE_MENU_ERROR_HTTPS")));
        }
        menu.setCreateBy(SecurityUtils.getUserId());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */

    @ApiOperation(value = "修改菜单")
    // @RequiresPermissions("system:menu:edit")
    //@Log(title = "系统管理", menu = "系统管理", subMenu = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error(MessageSource.M("UPDATE_MENU").concat(menu.getMenuName()).concat(MessageSource.M("ADD_UPDATE_MENU_ERROR")));
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error(MessageSource.M("UPDATE_MENU").concat(menu.getMenuName()).concat(MessageSource.M("ADD_UPDATE_MENU_ERROR_HTTPS")));
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return AjaxResult.error(MessageSource.M("UPDATE_MENU").concat(menu.getMenuName()).concat(MessageSource.M("ADD_UPDATE_MENU_ERROR_SELF")));
        }
        menu.setUpdateBy(SecurityUtils.getUserId());
        if (StringUtils.isEmpty(menu.getIcon())) {
            menu.setIcon("#");
        }
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */

    @ApiOperation(value = "删除菜单")
    //@RequiresPermissions("system:menu:remove")
    @Log(title = "系统管理", menu = "系统管理", subMenu = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error(MessageSource.M("DELETE_MENU_ERROR_HAD_SUB"));
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

    /**
     * 获取路由信息
     *
     * @return
     */
    @ApiOperation(value = "获取路由信息", notes = "YangLei")
    @GetMapping("/getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.getRouters(userId);
        return R.ok(menuService.buildMenus(menus));
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @ApiOperation(value = "获取菜单列表", notes = "YangLei")
    @GetMapping("/getList")
    public R<List<SysMenu>> getList() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menus);
    }


    /**
     * 获取菜单列表
     *
     * @return
     */
    @ApiOperation(value = "获取菜单列表-功能模块", notes = "wudi")
    @GetMapping("/getListExt")
    public R<MenuTreeByUserIdOut> getListExt() {
        Long userId = SecurityUtils.getUserId();
        MenuTreeByUserIdOut resp = menuService.selectMenuTreeByUserIdExt(userId);
        return R.ok(resp);
    }
}