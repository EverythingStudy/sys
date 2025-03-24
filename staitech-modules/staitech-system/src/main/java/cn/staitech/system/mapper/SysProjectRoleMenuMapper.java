package cn.staitech.system.mapper;


import cn.staitech.system.domain.SysProjectRoleMenuKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysProjectRoleMenuMapper {
    int deleteByPrimaryKey(SysProjectRoleMenuKey key);

    int insert(SysProjectRoleMenuKey record);

    int insertSelective(SysProjectRoleMenuKey record);

    List<SysProjectRoleMenuKey> selectMenuId(Long id);


    List<SysProjectRoleMenuKey> selectRoleId(Long id);
    
    SysProjectRoleMenuKey selectMenuRole(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int insertProjectRM(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int insertPorRMList(@Param("projectRM") List<SysProjectRoleMenuKey> sysProjectRoleMenuKey);
    int deletePorRMList(@Param("projectRM") List<SysProjectRoleMenuKey> sysProjectRoleMenuKey);
    
    int updateStatus(SysProjectRoleMenuKey sysProjectRoleMenuKey);
    
    int deleteRoleId(Long roleId);

}