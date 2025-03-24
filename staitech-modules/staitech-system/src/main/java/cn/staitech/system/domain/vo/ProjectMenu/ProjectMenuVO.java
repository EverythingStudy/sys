package cn.staitech.system.domain.vo.ProjectMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectMenuVO {
    
    /**
     * 角色ID
     */
//    @NotNull(message = "角色不可为空")
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;
    
    /**
     * 菜单ID
     */
//    @NotNull(message = "权限不可为空")
    @ApiModelProperty(value = "权限id", required = true)
    private Long menuId;
    
}
