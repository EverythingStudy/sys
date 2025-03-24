package cn.staitech.system.service;

import cn.staitech.system.domain.ProjectMember;

public interface ISysProjectMemberService {
    
    /**
     * 根据用户和项目查询详情
     *
     * @param projectMember 参数配置信息
     * @return 参数配置信息
     */
    ProjectMember select(ProjectMember projectMember);
}
