package cn.staitech.system.controller;

import cn.staitech.common.core.domain.R;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.system.domain.vo.project.ProjectDetailsOut;
import cn.staitech.system.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "公用接口", tags = "公用接口")
@RestController
@RequestMapping("/commonInterface")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    @ApiOperation(value = "查询项目详情接口")
    @GetMapping(value = "/projectDetail")
    @Log(title = "项目详情", menu = "项目详情", subMenu = "项目详情", businessType = BusinessType.QUERY)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataTypeClass = Long.class, paramType = "query", example = "1")})
    public R<ProjectDetailsOut> projectDetail(@RequestParam("projectId") Long projectId){
        return R.ok(projectService.projectDetails(projectId));
    }


}
