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
 * @Description ：角色编辑
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：SysRoleEditVO
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/6/7 星期三 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRoleUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;

    @ApiModelProperty("模块id")
    private Long moduleId;

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

    /**
     * 功能权限
     */
    @ApiModelProperty(value = "功能权限", required = true)
    private Long[] menuIds;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Size(min = 0, max = 500, message = "{SysRoleInsertVO.remark.length}")
    private String remark;

    @ApiModelProperty(value = "系统管理级别：1：是0：否")
    private Integer roleLevel;
}
