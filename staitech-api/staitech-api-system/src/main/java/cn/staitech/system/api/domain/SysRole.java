package cn.staitech.system.api.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import cn.staitech.common.core.annotation.Excel;
import cn.staitech.common.core.annotation.Excel.ColumnType;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表 sys_role
 *
 * @author staitech
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @Excel(name = "角色ID", cellType = ColumnType.NUMERIC)
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称(必填，只允许输入汉字，限制15个字)", required = true)
    @Excel(name = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 15, message = "角色名称长度不能超过15个汉字")
    private String roleName;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识(必填，只允许输入字母和数字)", required = true)
    @Excel(name = "权限标识")
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
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    @ApiModelProperty(value = "数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）", hidden = true)
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    @ApiModelProperty(value = "菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） ", hidden = true)
    private boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    @ApiModelProperty(value = "部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）", hidden = true)
    private boolean deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 角色状态名称（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态名称（0正常 1停用）")
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private String statusName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）", hidden = true)
    private String delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @ApiModelProperty(value = "用户是否存在此角色标识 默认不存在", hidden = true)
    private boolean flag = false;

    /**
     * 功能权限
     */
    @ApiModelProperty(value = "功能权限", required = true)
    private Long[] menuIds;


    @ApiModelProperty(value ="模块id", hidden = true)
    private Long moduleId;
    /**
     * 部门组（数据权限）
     */
    @ApiModelProperty(value = "部门组（数据权限）", hidden = true)
    private Long[] deptIds;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", hidden = true)
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者", hidden = true)
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Size(min = 0, max = 50, message = "角色描述长度过长")
    private String remark;

    @ApiModelProperty(value = "角色名称英文")
    private String roleNameEn;

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    public boolean isMenuCheckStrictly() {
        return menuCheckStrictly;
    }

    public void setMenuCheckStrictly(boolean menuCheckStrictly) {
        this.menuCheckStrictly = menuCheckStrictly;
    }

    public boolean isDeptCheckStrictly() {
        return deptCheckStrictly;
    }

    public void setDeptCheckStrictly(boolean deptCheckStrictly) {
        this.deptCheckStrictly = deptCheckStrictly;
    }

    public boolean isFlag() {
        return flag;
    }
}
