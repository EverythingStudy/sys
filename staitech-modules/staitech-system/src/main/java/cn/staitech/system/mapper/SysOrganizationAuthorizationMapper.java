package cn.staitech.system.mapper;




import cn.staitech.system.domain.SysOrganizationAuthorization;

import java.util.List;

/**
 * @author gjt
 */
public interface SysOrganizationAuthorizationMapper {

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
     * 获取详情
     * @param organizationId 机构id
     * @return int
     */
    SysOrganizationAuthorization selectById(Long organizationId);

    /**
     * 更新机构管理
     * @param organizationAuthorization 机构信息
     * @return int
     */
    int update(SysOrganizationAuthorization organizationAuthorization);

    
    
    

}