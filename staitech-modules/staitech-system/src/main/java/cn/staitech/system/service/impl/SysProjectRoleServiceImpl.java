package cn.staitech.system.service.impl;

import cn.staitech.system.api.domain.SysProjectRole;
import cn.staitech.system.mapper.SysProjectRoleMapper;
import cn.staitech.system.service.ISysProjectRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色 业务层处理
 *
 * @author staitech
 */
@Service
public class SysProjectRoleServiceImpl implements ISysProjectRoleService
{
    @Resource
    private SysProjectRoleMapper projectRoleMapper;



    /**
     * 根据条件分页查询角色数据
     *
     * @param projectRole 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysProjectRole> selectProjectRoleList(SysProjectRole projectRole)
    {
        return projectRoleMapper.selectProjectRoleList(projectRole);
    }
    
    /**
     * 根据主键查询单条信息
     *
     * @param roleId 角色id
     * @return 角色数据信息
     */
    @Override
    public SysProjectRole selectProjectRole(Long roleId){
        return projectRoleMapper.selectProjectRole(roleId);
    }

    /**
     * 根据条件查询项目角色数据总记录数
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public Integer countProjectRole(SysProjectRole role) {
        return projectRoleMapper.countProjectRole(role);
    }

    @Override
    public int insertProjectRole(SysProjectRole role) {
        return projectRoleMapper.insertProjectRole(role);
    }

    @Override
    public int deleteProjectRoleById(Long roleId) {
        return projectRoleMapper.deleteProjectRoleById(roleId);
    }

    @Override
    public int deleteProjectRoleByIds(Long[] roleIds) {
        return projectRoleMapper.deleteProjectRoleByIds(roleIds);
    }
    
    @Override
    public List<SysProjectRole> selectByProjectId(Long projectId){
        return projectRoleMapper.selectByProjectId(projectId);
    }
}
