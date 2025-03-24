package cn.staitech.system.mapper;



import cn.staitech.system.domain.ProjectMember;

import java.util.List;

public interface ProjectMemberMapper {
    
    /**
     * 根据用户和项目查询详情
     *
     * @param projectMember 参数配置信息
     * @return 参数配置信息
     */
    ProjectMember selectProject(ProjectMember projectMember);

}