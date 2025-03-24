package cn.staitech.system.service.impl;

import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.domain.vo.project.ProjectDetailsOut;
import cn.staitech.system.mapper.ProjectMapper;
import cn.staitech.system.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;


    /**
     * 项目详情
     * */
    @Override
    public ProjectDetailsOut projectDetails(Long projectId){
        ProjectDetailsOut projectDetailsOut= ProjectDetailsOut.builder().projectId(projectId).organizationId(SecurityUtils.getLoginUser().getSysUser().getOrganizationId()).build();
        return projectMapper.projectDetails(projectDetailsOut);
    }



}
