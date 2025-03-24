package cn.staitech.system.mapper;

import cn.staitech.system.domain.vo.project.ProjectDetailsOut;

public interface ProjectMapper {


    /**
     * 项目详情
     * */
    ProjectDetailsOut projectDetails(ProjectDetailsOut projectDetailsOut);


}
