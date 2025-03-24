package cn.staitech.system.domain.vo;

import cn.staitech.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Description ：
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：SysRoleInsertVO
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/6/7 星期三 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRoleInsertVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称(必填，只允许输入汉字，限制15个字)", required = true)
    @Excel(name = "角色名称")
    @NotBlank(message = "{SysRoleInsertVO.roleName.notNull}")
    @Size(min = 0, max = 500, message = "{SysRoleInsertVO.roleName.length}")
    private String roleName;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识(必填，只允许输入字母和数字)", required = true)
    @Excel(name = "权限标识")
    @NotBlank(message = "{SysRoleInsertVO.roleKey.notNull}")
    @Size(min = 0, max = 500, message = "{SysRoleInsertVO.roleKey.length}")
    private String roleKey;

    @ApiModelProperty(value = "系统管理级别：1：是0：否")
    private Integer roleLevel;

    /**
     * 功能权限
     */
    @ApiModelProperty(value = "功能权限", required = true)
    private Long[] menuIds;


    @ApiModelProperty(value ="模块id", hidden = true)
    private Long moduleId;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Size(min = 0, max = 500, message = "{SysRoleInsertVO.remark.length}")
    private String remark;
}
