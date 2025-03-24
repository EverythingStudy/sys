package cn.staitech.system.controller;


import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.SysProjectRole;
import cn.staitech.system.constant.SysProjectMenuConstant;
import cn.staitech.system.domain.ProjectMember;
import cn.staitech.system.domain.SysProjectMenu;
import cn.staitech.system.domain.SysProjectRoleMenuKey;
import cn.staitech.system.domain.vo.ProjectMenu.PermissionViewerVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProUpdateVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProjectMenuRepVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProjectMenuRoleVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProjectMenuUpdateVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProjectMenuVO;
import cn.staitech.system.domain.vo.ProjectMenu.ProjectUpdateVO;
import cn.staitech.system.service.ISysProjectMemberService;
import cn.staitech.system.service.ISysProjectMenuService;
import cn.staitech.system.service.ISysProjectRoleMenuService;
import cn.staitech.system.service.ISysProjectRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-03-23
 */
@RestController
@Api(value = "项目权限", tags = "项目权限")
@RequestMapping("/projectMenu")
public class SysProjectMenuController {
    @Resource
    private ISysProjectMenuService projectMenuService;
    
    @Resource
    private ISysProjectRoleMenuService projectRoleMenuService;
    
    @Resource
    private ISysProjectRoleService projectRoleService;
    
    @Resource
    private ISysProjectMemberService projectMemberService;
    
    @ApiOperation(value = "获取当前用户的权限")
    @PostMapping("/projectPermissions")
    public R<List<PermissionViewerVO>> projectPermissions(Long projectId) {
        
        ProjectMember projectMember = new ProjectMember();
        
        projectMember.setProjectId(projectId);
        
        projectMember.setUserId(SecurityUtils.getUserId());
        
        ProjectMember projectMemberBy = projectMemberService.select(projectMember);
        
        Long roleId = null;
        if (projectMemberBy != null) {
            roleId = projectMemberBy.getRoleId();
        }
        int roleType = 0;
        if (roleId != null) {
            SysProjectRole projectRoleBy = projectRoleService.selectProjectRole(roleId);
            if (projectRoleBy != null) {
                roleType = projectRoleBy.getRoleType();
            }
        }
        // 获取所有权限
        List<SysProjectMenu> sysProjectMenuList = projectMenuService.selectList();
        List<SysProjectRoleMenuKey> projectRoleMenuKeyList = projectRoleMenuService.selectRoleId(roleId);
        List<PermissionViewerVO> permissionViewerVOList = new ArrayList<>();
        
        for (SysProjectMenu projectMenu : sysProjectMenuList) {
            PermissionViewerVO permissionViewerVO = new PermissionViewerVO();
            
            if (roleType == 1) {
                permissionViewerVO.setPermission(projectMenu.getPerms());
                permissionViewerVO.setMenuType(projectMenu.getMenuType());
                List<String> list = Arrays.asList(projectMenu.getPerms().split(":"));
                String perms = list.get(list.size() - 1);
                permissionViewerVO.setPerms(perms);
                permissionViewerVOList.add(permissionViewerVO);
                
            } else {
                for (SysProjectRoleMenuKey projectRoleMenuKey : projectRoleMenuKeyList) {
                    if (Objects.equals(projectMenu.getMenuId(), projectRoleMenuKey.getMenuId())) {
                        permissionViewerVO.setPermission(projectMenu.getPerms());
                        permissionViewerVO.setMenuType(projectMenu.getMenuType());
                        List<String> list = Arrays.asList(projectMenu.getPerms().split(":"));
                        String perms = list.get(list.size() - 1);
                        permissionViewerVO.setPerms(perms);
                        permissionViewerVOList.add(permissionViewerVO);
                    }
                }
            }
        }
        return R.ok(permissionViewerVOList);
    }
    
    
    @ApiOperation(value = "查询项目权限接口")
    @PostMapping("/list")
    public R<HashMap<String, List<ProjectMenuRepVO>>> list(Long projectId) {
        // 获取当前用户的权限在此项目中的权限
        
        ProjectMember projectMember = new ProjectMember();
        
        projectMember.setProjectId(projectId);
        
        projectMember.setUserId(SecurityUtils.getUserId());
        
        ProjectMember projectMemberBy = projectMemberService.select(projectMember);
        
        Long roleId = null;
        if (projectMemberBy != null) {
            roleId = projectMemberBy.getRoleId();
        }
        int roleType = 0;
        if (roleId != null) {
            SysProjectRole projectRoleBy = projectRoleService.selectProjectRole(roleId);
            if (projectRoleBy != null) {
                roleType = projectRoleBy.getRoleType();
            }
        }
        // 查询出当前用户之后，获取当前用户的角色
        List<SysProjectMenu> menuManageList = new ArrayList<>();
        List<SysProjectMenu> menuContributeList = new ArrayList<>();
        List<SysProjectRole> sysProjectRoleList = projectRoleService.selectByProjectId(projectId);
        Long manageRoleId = null;
        Long ContributeRoleId = null;
        Long manageStatus = null;
        Long contributeStatus = null;
        if (roleType == 1 || roleType == 2) {
            manageStatus = 1L;
            contributeStatus = 1L;
        }
        for (SysProjectRole projectRole : sysProjectRoleList) {
            if (projectRole.getRoleType() == 2) {
                List<SysProjectRoleMenuKey> projectRoleMenuKeyList = projectRoleMenuService.selectRoleId(
                        projectRole.getRoleId());
                for (SysProjectRoleMenuKey projectRoleMenu : projectRoleMenuKeyList) {
                    menuManageList.add(projectMenuService.selectByPrimaryKey(projectRoleMenu.getMenuId()));
                }
                manageRoleId = projectRole.getRoleId();
                
            } else if (projectRole.getRoleType() == 3) {
                List<SysProjectRoleMenuKey> projectRoleMenuKeyList = projectRoleMenuService.selectRoleId(
                        projectRole.getRoleId());
                for (SysProjectRoleMenuKey projectRoleMenu : projectRoleMenuKeyList) {
                    menuContributeList.add(projectMenuService.selectByPrimaryKey(projectRoleMenu.getMenuId()));
                    
                }
                ContributeRoleId = projectRole.getRoleId();
            }
        }
        List<SysProjectMenu> sysProjectMenuList = projectMenuService.selectList();
        List<ProjectMenuRepVO> projectMenuRepVOList = new ArrayList<>();
        for (SysProjectMenu projectMenu : sysProjectMenuList) {
            ProjectMenuRepVO projectMenuRepVO = new ProjectMenuRepVO();
            BeanUtils.copyBeanProp(projectMenuRepVO, projectMenu);
            projectMenuRepVO.setContributeRoleId(ContributeRoleId);
            projectMenuRepVO.setManageRoleId(manageRoleId);
            projectMenuRepVO.setManageStatus(manageStatus);
            projectMenuRepVO.setContributeStatus(contributeStatus);
            projectMenuRepVOList.add(projectMenuRepVO);
        }
        HashMap<String, List<ProjectMenuRepVO>> stringListHashMap = new HashMap<>();
        List<ProjectMenuRepVO> sysProjectMenuList1 = new ArrayList<>();
        List<ProjectMenuRepVO> sysProjectMenuList2 = new ArrayList<>();
        List<ProjectMenuRepVO> sysProjectMenuList3 = new ArrayList<>();
        for (ProjectMenuRepVO sysProjectMenu : projectMenuRepVOList) {
            for (SysProjectMenu projectMenu : menuManageList) {
                if (Objects.equals(projectMenu.getMenuId(), sysProjectMenu.getMenuId())) {
                    sysProjectMenu.setManage(1L);
                }
            }
            for (SysProjectMenu projectMenu : menuContributeList) {
                if (Objects.equals(projectMenu.getMenuId(), sysProjectMenu.getMenuId())) {
                    sysProjectMenu.setContribute(1L);
                }
            }
            if (Objects.equals(sysProjectMenu.getMenuType(), SysProjectMenuConstant.SIDEBAR)) {
                sysProjectMenuList1.add(sysProjectMenu);
            } else if (Objects.equals(sysProjectMenu.getMenuType(), SysProjectMenuConstant.MARKING)) {
                sysProjectMenuList2.add(sysProjectMenu);
                
            } else if (Objects.equals(sysProjectMenu.getMenuType(), SysProjectMenuConstant.MEASURE)) {
                sysProjectMenuList3.add(sysProjectMenu);
            }
        }
        stringListHashMap.put(SysProjectMenuConstant.SIDEBAR, sysProjectMenuList1);
        stringListHashMap.put(SysProjectMenuConstant.MARKING, sysProjectMenuList2);
        stringListHashMap.put(SysProjectMenuConstant.MEASURE, sysProjectMenuList3);
        return R.ok(stringListHashMap);
    }
    
    @ApiOperation(value = "批量更改权限状态")
    @PostMapping("/updateList")
    public R<ProUpdateVO> updateList(@Validated @RequestBody ProjectMenuUpdateVO req) {
        List<SysProjectRole> sysProjectRoleList = projectRoleService.selectByProjectId(req.getProjectId());
        
        Long contributeRoleId = null;
        for (SysProjectRole sysProjectRole : sysProjectRoleList) {
            if (sysProjectRole.getRoleType() == 3) {
                contributeRoleId = sysProjectRole.getRoleId();
            }
        }
        ProUpdateVO proUpdateVO = new ProUpdateVO();
        List<SysProjectMenu> projectMenuList = projectMenuService.selectList();
        if (req.getStatus() == 1) {
            projectRoleMenuService.deleteRoleId(contributeRoleId);
            for (SysProjectMenu projectMenu : projectMenuList) {
                if (projectMenu.getMenuId() != 1) {
                    SysProjectRoleMenuKey projectRoleMenu = new SysProjectRoleMenuKey();
                    projectRoleMenu.setRoleId(contributeRoleId);
                    projectRoleMenu.setMenuId(projectMenu.getMenuId());
                    projectRoleMenu.setCreateBy(SecurityUtils.getUserId());
                    projectRoleMenuService.insertProjectRM(projectRoleMenu);
                }
            }
            proUpdateVO.setStatus(1L);
        } else if (req.getStatus() == 0) {
            projectRoleMenuService.deleteRoleId(contributeRoleId);
            proUpdateVO.setStatus(0L);
        }
        return R.ok(proUpdateVO);
    }
    
    @ApiOperation(value = "更改权限状态")
    @PostMapping("/update")
    public R<List<ProjectUpdateVO>> update(@Validated @RequestBody ProjectMenuRoleVO req) {
        
        // 进行实例化
        SysProjectRoleMenuKey projectRoleMenu = new SysProjectRoleMenuKey();
        List<ProjectUpdateVO> projectUpdateVOList = new ArrayList<>();
        for (ProjectMenuVO projectMenu : req.getProjectMenu()) {
            projectRoleMenu.setMenuId(projectMenu.getMenuId());
            projectRoleMenu.setRoleId(projectMenu.getRoleId());
            projectRoleMenu.setCreateBy(SecurityUtils.getUserId());
            SysProjectRoleMenuKey sysProjectRoleMenuKeyBy = projectRoleMenuService.selectMenuRole(projectRoleMenu);
            
            if (!Optional.ofNullable(sysProjectRoleMenuKeyBy).isPresent()) {
                // projectRoleMenuService.deleteByPrimaryKey(projectRoleMenu);
                ProjectUpdateVO projectUpdateVO = new ProjectUpdateVO();
                projectUpdateVO.setMenuId(projectMenu.getMenuId());
                projectUpdateVO.setRoleId(projectMenu.getRoleId());
                projectUpdateVO.setStatus(0L);
                projectUpdateVOList.add(projectUpdateVO);
                projectRoleMenuService.insertProjectRM(projectRoleMenu);
            } else {
                ProjectUpdateVO projectUpdateVO = new ProjectUpdateVO();
                projectUpdateVO.setMenuId(projectMenu.getMenuId());
                projectUpdateVO.setRoleId(projectMenu.getRoleId());
                projectUpdateVO.setStatus(0L);
                projectRoleMenuService.deleteByPrimaryKey(projectRoleMenu);
                projectUpdateVOList.add(projectUpdateVO);
            }
        }
        return R.ok(projectUpdateVOList);
    }
    
    
}

