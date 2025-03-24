package cn.staitech.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.exception.ServiceException;
import cn.staitech.common.core.utils.SpringUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.SysRole;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.domain.SysMenu;
import cn.staitech.system.domain.SysRoleDept;
import cn.staitech.system.domain.SysRoleMenu;
import cn.staitech.system.domain.SysUserRole;
import cn.staitech.system.domain.vo.RoleInfoOut;
import cn.staitech.system.domain.vo.SysRoleQueryVO;
import cn.staitech.system.mapper.*;
import cn.staitech.system.service.ISysRoleService;
import cn.staitech.system.utils.MessageSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static cn.staitech.common.core.constant.SysRoleConstant.*;
import static cn.staitech.common.core.constant.UserConstants.*;
import static cn.staitech.common.core.utils.SysRoleUtil.getSort;

/**
 * 角色 业务层处理
 *
 * @author staitech
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {
    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleDeptMapper roleDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuMapper menuMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
//    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRoleQueryVO role) {
        List<SysRole> roleList = roleMapper.selectRoleList(role);
        roleList.forEach(o -> {
            o.setRoleLevelName(ROLE_LEVEL.get(o.getRoleLevel()));
            o.setStatusName(ROLE_STATUS.get(o.getStatus()));
        });
        return roleList;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRoleQueryVO());
    }

    /**
     * 角色下拉框
     *
     * @return
     */
    @Override
    public List<SysRole> optionselect() {
        List<SysRole> roles = roleMapper.selectRoleList(new SysRoleQueryVO());
        return roles;
/*        Long userId = SecurityUtils.getUserId();
        if (SysUser.isAdmin(userId)) {
            return roles;
        } else {
            List<SysRole> resp = roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
            return resp;
        }*/
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return FALSE;
        }
        return TRUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return FALSE;
        }
        return TRUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new ServiceException(MessageSource.M("DISALLOW_OPER_ADMIN"));
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysRoleQueryVO role = new SysRoleQueryVO();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles)) {
                throw new ServiceException(MessageSource.M("DISSALLOW_ACCESS_DATA"));
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return sysUserMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRole role) {
        if (!role.getRoleName().matches(VERIFY_CHINESE)) {
            throw new ServiceException(MessageSource.M("ADD_SYS_ROLE").concat("'").concat(role.getRoleName()).concat("'").concat(MessageSource.M("ADD_SYS_ROLE_ERROR")));
        }
        if (!role.getRoleKey().matches(VERIFY_LETTER_NUMBER)) {
            throw new ServiceException(MessageSource.M("ADD_SYS_ROLE").concat("'").concat(role.getRoleName()).concat("'").concat(MessageSource.M("ADD_SYS_ROLE_ERROR_VAL")));
        }

        // 设置角色编号
        List<SysRole> sysRoles = roleMapper.selectRoleList(new SysRoleQueryVO());
        if (ObjectUtil.isEmpty(sysRoles)) {
            role.setRoleSort(SYSTEM);
        } else {
            String roleSortLatest = sysRoles.get(0).getRoleSort();
            role.setRoleSort(getSort(roleSortLatest));
        }
        role.setCreateBy(SecurityUtils.getUserId());
        role.setUpdateBy(SecurityUtils.getUserId());

        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRole role) {
        role.setUpdateBy(SecurityUtils.getUserId());
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色id
     * @return 结果
     */
    @Override
    public int updateRoleStatus(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        if (ObjectUtil.isNull(role)) {
            throw new ServiceException(String.format(MessageSource.M("CURRENT_ROLE") + "ID:%1$s," + MessageSource.M("NODATA"), roleId));
        }
        if (countUserRoleByRoleId(roleId) > 0 && UNIQUE.equals(role.getStatus())) {
            throw new ServiceException(String.format(MessageSource.M("CURRENT_ROLE") + "%1$s" + MessageSource.M("DELETE_USER_ERROR_INUSE_USER"), role.getRoleName()));
        }
        if (StringUtils.isNotEmpty(role.getStatus()) && UNIQUE.equals(role.getStatus())) {
            role.setStatus(NOT_UNIQUE);
        } else {
            role.setStatus(UNIQUE);
        }
        role.setUpdateBy(SecurityUtils.getUserId());
        return roleMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authDataScope(SysRole role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(role.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            list.add(sysRoleMenu);
        }

        List<Long> longs = Arrays.asList(role.getMenuIds());
        if(CollectionUtils.isNotEmpty(longs)&&!longs.contains(role.getModuleId())){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(role.getRoleId());
            sysRoleMenu.setMenuId(role.getModuleId());
            list.add(sysRoleMenu);
        }

        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId) {
        SysRole sysRole = roleMapper.selectRoleById(roleId);
        if (ObjectUtil.isNull(sysRole)) {
            throw new ServiceException(String.format(MessageSource.M("CURRENT_ROLE") + "ID:%1$d," + MessageSource.M("NODATA"), roleId));
        }
        if (UNIQUE.equals(sysRole.getStatus())) {
            throw new ServiceException(String.format(MessageSource.M("CURRENT_ROLE") + "%1$s" + MessageSource.M("DELETE_ERROR_INUSE"), sysRole.getRoleName()));
        }
        if (countUserRoleByRoleId(roleId) > 0) {
            throw new ServiceException(String.format(MessageSource.M("CURRENT_ROLE") + "%1$s" + MessageSource.M("DELETE_USER_ERROR_INUSE_USER"), sysRole.getRoleName()));
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s" + MessageSource.M("DELETE_ERROR_DISTRIBUTION"), role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }

    @Override
    public R<RoleInfoOut> getInfoById(Long roleId) {
        log.info("根据id获得角色详情接口开始：");
        SysRole sysRole = roleMapper.selectRoleById(roleId);
        if(ObjectUtil.isEmpty(sysRole)){

        }
        RoleInfoOut resp = new RoleInfoOut();
        BeanUtils.copyProperties(sysRole, resp);
        List<SysMenu> menuList = menuMapper.getMenuList(roleId);
        List<Long> retData = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(menuList)){
            for (SysMenu sysMenu : menuList) {
                if(!retData.contains(sysMenu.getMenuId())){
                    retData.add(sysMenu.getMenuId());
                }

                if(sysMenu.getParentId()==0L){
                    resp.setModuleId(sysMenu.getMenuId());
                }
            }
        }
        resp.setMenuIds(retData);
        return R.ok(resp);
    }
}
