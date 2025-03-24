package cn.staitech.system.service;




import cn.staitech.system.domain.SysOrganization;
import cn.staitech.system.domain.SysOrganizationAuthorization;
import cn.staitech.system.domain.vo.organization.*;

import java.text.ParseException;
import java.util.List;

/**
 *
 * @author staitech
 */

public interface SysOrganizationService {

    /**
     * 根据主键查询详情
     * &#064;params  organizationId
     * @return SysOrganization
     */
    OrganizationSelectResVo selectPrimaryKey(Long organizationId);
    
    SysOrganizationAuthorization selectOrganizationAuthorizationPrimaryKey(Long organizationId);


    /**
     * 添加机构管理
     * @param organization 机构信息
     * @return int
     */
    int insert(OrganizationInsertVo organization);

    /**
     * 根据条件查询数据
     * @param organization
     * @return int
     */
    List<OrganizationSelectResVo> selectList(OrganizationSelectVo organization);

    /**
     * 更新机构管理
     * @param organization
     * @return int
     */
    int delete(Long organizationId) throws ParseException;

    /**
     * 根据名称查询数据
     * @param organization
     * @return int
     */
    SysOrganization selectName(SysOrganization organization);

    /**
     * 更新机构管理
     * @param organization
     * @return int
     */
    int update(OrganizationUpdateVo organization);

    /**
     * 更新机构表中状态
     * @param req
     * @return
     */
    int updateStatus(OrganizationUpdateStatusVo req);
    

}