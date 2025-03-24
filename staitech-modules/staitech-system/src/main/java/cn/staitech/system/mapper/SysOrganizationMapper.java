package cn.staitech.system.mapper;



import cn.staitech.system.domain.SpecialImage;
import cn.staitech.system.domain.SubImage;
import cn.staitech.system.domain.SysOrganization;
import cn.staitech.system.domain.vo.organization.OrganizationSelectResVo;
import cn.staitech.system.domain.vo.organization.OrganizationSelectVo;

import java.util.List;

/**
 * @author gjt
 */
public interface SysOrganizationMapper {

    /**
     * 根据主键查询详情
     * &#064;params  organizationId
     * @return SysOrganization
     */
    OrganizationSelectResVo selectPrimaryKey(Long organizationId);

    /**
     * 添加机构管理
     * @param organization
     * @return int
     */
    int insert(SysOrganization organization);

    /**
     * 添加机构管理
     * @param organization
     * @return int
     */
    List<SubImage> selectImageCount(Long organization);

    /**
     * 根据条件查询数据
     * @param organization
     * @return int
     */
    List<OrganizationSelectResVo> selectList(OrganizationSelectVo organization);

    /**
     * 根据名称查询数据
     * @param organization
     * @return int
     */
    SysOrganization selectName(SysOrganization organization);

    /**
     * 更新机构管理状态
     * @param organization
     * @return int
     */
    int delete(SysOrganization organization);

    /**
     * 更新机构管理
     * @param organization
     * @return int
     */
    int update(SysOrganization organization);

    int selectSpecialCount(SpecialImage specialImage);


    Integer selectOrganizationCode();
}