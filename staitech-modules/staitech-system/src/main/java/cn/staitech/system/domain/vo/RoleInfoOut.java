package cn.staitech.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleInfoOut {
    @ApiModelProperty("模块id")
    private Long moduleId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称(必填，只允许输入汉字，限制15个字)", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 15, message = "角色名称长度不能超过15个汉字")
    private String roleName;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识(必填，只允许输入字母和数字)", required = true)
    @NotBlank(message = "权限标识不能为空")
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    private String roleKey;

    /**
     * 角色编号
     */
    @ApiModelProperty(value = "角色编号")
    private String roleSort;

    /**
     * 角色级别
     */
    @ApiModelProperty(value = "角色级别")
    private Integer roleLevel;

    /**
     * 角色级别名称
     */
    @ApiModelProperty(value = "角色级别名称")
    private String roleLevelName;

    /**
     * 功能权限
     */
    @ApiModelProperty(value = "功能权限", required = true)
    private List<Long> menuIds;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Size(min = 0, max = 50, message = "角色描述长度过长")
    private String remark;

    @ApiModelProperty(value = "角色名称英文")
    private String roleNameEn;

}