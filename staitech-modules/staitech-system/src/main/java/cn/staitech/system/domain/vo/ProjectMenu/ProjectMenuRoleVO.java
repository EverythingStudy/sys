package cn.staitech.system.domain.vo.ProjectMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectMenuRoleVO {
    
    /**
     * 权限角色列表
     */
    //    @NotNull(message = "权限不可为空")
    @ApiModelProperty(value = "权限角色列表", required = true)
    private List<ProjectMenuVO> projectMenu;
    
}
