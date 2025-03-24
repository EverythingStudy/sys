package cn.staitech.system.service;


import cn.staitech.system.domain.SysOrganizationAuthorization;

import java.util.List;

/**
 * @author admin
 */

public interface SysOrganizationAuthorizationService {


    /**
     * 根据主键查询详情
     * &#064;params  organizationId
     * @return SysOrganization
     */
    int insert(SysOrganizationAuthorization organizationAuthorization);

    /**
     * 查询机构信息
     * &#064;params  organizationId
     * @return SysOrganization
     */
    List<SysOrganizationAuthorization> selectList(SysOrganizationAuthorization organizationAuthorization);

    /**
     * 更新机构管理
     * @param organizationAuthorization
     * @return int
     */
    int update(SysOrganizationAuthorization organizationAuthorization);

}
