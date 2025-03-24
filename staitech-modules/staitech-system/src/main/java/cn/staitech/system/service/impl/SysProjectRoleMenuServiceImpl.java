package cn.staitech.system.service.impl;

import cn.staitech.system.domain.SysProjectRoleMenuKey;
import cn.staitech.system.mapper.SysProjectRoleMenuMapper;
import cn.staitech.system.service.ISysProjectRoleMenuService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysProjectRoleMenuServiceImpl implements ISysProjectRoleMenuService {
    
    @Resource
    private SysProjectRoleMenuMapper sysProjectRoleMenumapper;
    
    
    @Override
    public SysProjectRoleMenuKey selectMenuRole(SysProjectRoleMenuKey sysProjectRoleMenuKey) {
        return sysProjectRoleMenumapper.selectMenuRole(sysProjectRoleMenuKey);
    }
    
    @Override
    public int insertProjectRM(SysProjectRoleMenuKey sysProjectRoleMenuKey) {
        return sysProjectRoleMenumapper.insertProjectRM(sysProjectRoleMenuKey);
    }
    
    @Override
    public int deleteByPrimaryKey(SysProjectRoleMenuKey sysProjectRoleMenuKey) {
        return sysProjectRoleMenumapper.deleteByPrimaryKey(sysProjectRoleMenuKey);
    }
    
    @Override
    public int insertPorRMList(@Param("projectRM") List<SysProjectRoleMenuKey> sysProjectRoleMenuKey) {
        return sysProjectRoleMenumapper.insertPorRMList(sysProjectRoleMenuKey);
    }
    
    @Override
    public List<SysProjectRoleMenuKey> selectRoleId(Long roleId){
        return sysProjectRoleMenumapper.selectRoleId(roleId);
    }
    
    @Override
    public int updateStatus(SysProjectRoleMenuKey sysProjectRoleMenuKey){
        return sysProjectRoleMenumapper.updateStatus(sysProjectRoleMenuKey);
    }
    
    @Override
    public List<SysProjectRoleMenuKey> selectMenuId(Long id){
        return sysProjectRoleMenumapper.selectMenuId(id);
    }
    
    @Override
    public int deleteRoleId(Long roleId){
        return sysProjectRoleMenumapper.deleteRoleId(roleId);
    }
    
}
