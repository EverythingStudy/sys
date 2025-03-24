package cn.staitech.system.service.impl;


import cn.staitech.common.core.exception.ServiceException;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.domain.SysOrganization;
import cn.staitech.system.domain.SysOrganizationAuthorization;
import cn.staitech.system.domain.vo.organization.*;
import cn.staitech.system.enums.OrganizationStatusEnum;
import cn.staitech.system.mapper.SysOrganizationAuthorizationMapper;
import cn.staitech.system.mapper.SysOrganizationMapper;
import cn.staitech.system.service.SysOrganizationService;
import cn.staitech.system.utils.MessageSource;
import com.ibm.icu.text.SimpleDateFormat;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cn.staitech.system.enums.OrganizationEnum.DEL_FLAG_2;

/**
 * 标注 服务层实现 .
 *
 * @author staitech
 */
@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {
    @Resource
    private SysOrganizationMapper organizationMapper;
    @Resource
    private SysOrganizationAuthorizationMapper organizationAuthorizationMapper;

    private String basePath = File.separator + "home" + File.separator + "pacmvs";


    @Override
    public SysOrganizationAuthorization selectOrganizationAuthorizationPrimaryKey(Long organizationId) {
        SysOrganizationAuthorization zation = new SysOrganizationAuthorization();
        if (!Optional.ofNullable(organizationId).isPresent()) {
            throw new ServiceException(MessageSource.M("ORG_DISALLOW_NULL"));
        }
        SysOrganizationAuthorization author = new SysOrganizationAuthorization();
        author.setOrganizationId(organizationId);
        List<SysOrganizationAuthorization> list = organizationAuthorizationMapper.selectList(author);
        if (CollectionUtils.isNotEmpty(list)) {
            zation = list.get(0);
        }
        return zation;
    }

    /**
     * 根据主键查询详情
     * &#064;params  organizationId
     *
     * @return SysOrganization
     */
    @Override
    public OrganizationSelectResVo selectPrimaryKey(Long organizationId) {
        if (!Optional.ofNullable(organizationId).isPresent()) {
            throw new ServiceException(MessageSource.M("ORG_DISALLOW_NULL"));
        }
        OrganizationSelectResVo organizationBys = organizationMapper.selectPrimaryKey(organizationId);
        if (organizationBys != null) {
            organizationBys.setStatusName(OrganizationStatusEnum.getOrganizationByValue(organizationBys.getStatus()));
        }
        return organizationBys;
    }

    /**
     * 添加机构管理
     *
     * @param req 入参
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(OrganizationInsertVo req) {
        SysOrganization organization = new SysOrganization();
        organization.setOrganizationName(req.getOrganizationName());
        SysOrganization organizationList = organizationMapper.selectName(organization);
        if (organizationList != null) {
            throw new ServiceException(MessageSource.M("ORG_HAD_SAME"));
        }
        BeanUtils.copyProperties(req, organization);
        //查询编号
        Integer organizationCode = organizationMapper.selectOrganizationCode();
        organization.setOrganizationNumber(organizationCode + 1);
        int res = organizationMapper.insert(organization);
        if (res > 0) {
            SysOrganizationAuthorization organizationAuthorization = new SysOrganizationAuthorization();
            organizationAuthorization.setOrganizationId(organization.getOrganizationId());
            BeanUtils.copyProperties(req, organizationAuthorization);
            organizationAuthorizationMapper.insert(organizationAuthorization);
            // 机构添加成功后，创建机构下的初始化文件
            createInitFile(organization.getOrganizationId());
        }
        return res;
    }

    public void createInitFile(Long organizationId) {
        String organizationFileName = "C" + geFourNumber(Math.toIntExact(organizationId));
        String organizationFileUrl = basePath + File.separator + organizationFileName;
        File organizationFile = new File(organizationFileUrl);
        if(!organizationFile.exists() && !organizationFile.isDirectory()){
            // 创建文件夹下的默认文件夹
            if(organizationFile.mkdirs()){
                String data = organizationFileUrl + File.separator + "Data";
                String slides = organizationFileUrl + File.separator + "Slides";
                String upload = organizationFileUrl + File.separator + "Upload";
                createFile(data);
                createFile(slides);
                if(createFile(upload)){
                    String zip = upload + File.separator + "json" + File.separator + "zip";
                    createFile(zip);
                }
            }
        }
    }

    public Boolean createFile(String url) {
        File file = new File(url);
        return file.mkdirs();
    }



    public static String geFourNumber(int number){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(3);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }

    /**
     * 根据条件查询数据
     *
     * @param organization
     * @return int
     */
    @Override
    public List<OrganizationSelectResVo> selectList(OrganizationSelectVo organization) {

        List<OrganizationSelectResVo> organizationSelectResVos = organizationMapper.selectList(organization);
        // 将机构中的状态转化为中文
        for (OrganizationSelectResVo organizationRes : organizationSelectResVos) {
            organizationRes.setStatusName(OrganizationStatusEnum.getOrganizationByValue(organizationRes.getStatus()));
        }

        return organizationSelectResVos;
    }

    /**
     * 更新机构管理
     *
     * @param organizationId 机构id
     * @return int
     */
    @Override
    public int delete(Long organizationId) throws ParseException {
        if (!Optional.ofNullable(organizationId).isPresent()) {
            throw new ServiceException(MessageSource.M("ARGUMENT_ERROR"));
        }
        // 判断当前人数是否为0
        SysOrganizationAuthorization authorization = organizationAuthorizationMapper.selectById(organizationId);
        if (authorization.getAuthorizationMemberUsed() > 0) {
            throw new ServiceException(MessageSource.M("DELETE_ERROR_ORG_HAD_USER"));
        }
        // 删除机构时，校验是否到期,到期不可删除
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expirationTime = ft.parse(authorization.getExpirationTime());
        Date date = new Date();
        if (date.before(expirationTime)) {
            throw new ServiceException(MessageSource.M("DELETE_ERROR_ORG_INUSE"));
        }
        SysOrganization organization = new SysOrganization();
        organization.setDelFlag(DEL_FLAG_2.value());
        organization.setUpdateBy(SecurityUtils.getUserId());
        organization.setOrganizationId(organizationId);
        return organizationMapper.delete(organization);
    }

    /**
     * 根据名称查询数据
     *
     * @param organization
     * @return int
     */
    @Override
    public SysOrganization selectName(SysOrganization organization) {
        return organizationMapper.selectName(organization);
    }

    /**
     * 更新机构管理
     *
     * @param req 更新的参数信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(OrganizationUpdateVo req) {
        SysOrganization organization = new SysOrganization();
        organization.setOrganizationId(req.getOrganizationId());
        // 根据机构名称查询机构是否存在
        organization.setOrganizationName(req.getOrganizationName());
        SysOrganization organizationBy = organizationMapper.selectName(organization);
        if (organizationBy != null) {
            throw new ServiceException(MessageSource.M("DELETE_ERROR_ORG_HAD"));
        }
        // 查询更新前信息
        OrganizationSelectResVo organizationBys = organizationMapper.selectPrimaryKey(req.getOrganizationId());
        // 编辑机构时，不能修改成授权人数比当前用户数小
        if (organizationBys.getAuthorizationMemberUsed() > req.getAuthorizationMemberLimit()) {
            throw new ServiceException(MessageSource.M("DELETE_ERROR_ORG_MEMBER_COUNT"));
        }
        BeanUtils.copyProperties(req, organization);
        organization.setUpdateBy(SecurityUtils.getUserId());
        int res = organizationMapper.update(organization);
        // 更新机构认证表中信息
        if (res > 0) {
            SysOrganizationAuthorization organizationAuthorization = new SysOrganizationAuthorization();
            BeanUtils.copyProperties(req, organizationAuthorization);
            organization.setUpdateBy(SecurityUtils.getUserId());
            organizationAuthorizationMapper.update(organizationAuthorization);
        }
        return organizationMapper.update(organization);
    }


    @Override
    public int updateStatus(OrganizationUpdateStatusVo req) {
        SysOrganization organization = new SysOrganization();
        organization.setOrganizationId(req.getOrganizationId());
        organization.setStatus(req.getStatus());
        organization.setUpdateBy(SecurityUtils.getUserId());
        organizationMapper.update(organization);
        return organizationMapper.update(organization);
    }

}
