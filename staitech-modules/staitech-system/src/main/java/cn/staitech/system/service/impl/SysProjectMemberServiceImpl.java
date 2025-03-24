package cn.staitech.system.service.impl;

import cn.staitech.system.domain.ProjectMember;
import cn.staitech.system.mapper.ProjectMemberMapper;
import cn.staitech.system.service.ISysProjectMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangf
 */
@Service
public class SysProjectMemberServiceImpl implements ISysProjectMemberService {
    
    @Resource
    private ProjectMemberMapper projectMemberMapper;
    
    @Override
    public ProjectMember select(ProjectMember projectMember) {
        return projectMemberMapper.selectProject(projectMember);
    }
}
