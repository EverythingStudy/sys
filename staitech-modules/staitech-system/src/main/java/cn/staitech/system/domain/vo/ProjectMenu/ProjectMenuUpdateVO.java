package cn.staitech.system.domain.vo.ProjectMenu;

import cn.staitech.system.domain.SysProjectRoleMenuKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class ProjectMenuUpdateVO {
    
    /**
     * 权限角色列表
     */
    @NotNull(message = "{ProjectMenuUpdateVO.projectId.notNull}")
    @ApiModelProperty(value = "项目id", required = true)
    private Long projectId;
    
    /**
     * 状态
     */
    @NotNull(message = "{ProjectMenuUpdateVO.status.notNull}")
    @ApiModelProperty(value = "状态(0:删除,1:添加)",required = true)
    private Long status;
    
    

}
