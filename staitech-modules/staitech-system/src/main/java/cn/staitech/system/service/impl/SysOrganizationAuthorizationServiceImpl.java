package cn.staitech.system.service.impl;


import cn.staitech.system.domain.SysOrganizationAuthorization;
import cn.staitech.system.mapper.SysOrganizationAuthorizationMapper;
import cn.staitech.system.service.SysOrganizationAuthorizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gjt
 */
@Service
public class SysOrganizationAuthorizationServiceImpl implements SysOrganizationAuthorizationService {

    @Resource
    private SysOrganizationAuthorizationMapper organizationAuthorizationMapper;

    /**
     * 添加机构信息
     * &#064;params  organizationId
     * @return SysOrganization
     */
    @Override
    public int insert(SysOrganizationAuthorization organizationAuthorization){
        return organizationAuthorizationMapper.insert(organizationAuthorization);
    }

    /**
     * 查询机构信息
     * &#064;params  organizationId
     * @return SysOrganization
     */
    @Override
    public List<SysOrganizationAuthorization> selectList(SysOrganizationAuthorization organizationAuthorization){
        return organizationAuthorizationMapper.selectList(organizationAuthorization);
    }


    /**
     * 更新机构管理
     * @param organizationAuthorization
     * @return int
     */
    @Override
    public int update(SysOrganizationAuthorization organizationAuthorization){
        return organizationAuthorizationMapper.update(organizationAuthorization);
    }


}
