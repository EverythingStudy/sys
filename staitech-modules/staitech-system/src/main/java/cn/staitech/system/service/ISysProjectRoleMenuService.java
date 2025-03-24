package cn.staitech.system.service;

import cn.staitech.system.domain.SysProjectRoleMenuKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISysProjectRoleMenuService {
    
    SysProjectRoleMenuKey selectMenuRole(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int insertProjectRM(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int deleteByPrimaryKey(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int insertPorRMList(List<SysProjectRoleMenuKey> sysProjectRoleMenuKey);
    
    List<SysProjectRoleMenuKey> selectRoleId(Long roleId);
    
    int updateStatus(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    List<SysProjectRoleMenuKey> selectMenuId(Long id);
    
    int deleteRoleId(Long roleId);
    
}
