package cn.staitech.system.domain.vo.ProjectMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateVO {

    private Long roleId;
    

    private Long menuId;
    
    /**
     * 项目管理者 1:存在
     */
    private Long status;

    
    
}
