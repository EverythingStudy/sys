package cn.staitech.system.api.domain;


import cn.staitech.common.core.annotation.Excel;
import cn.staitech.common.core.web.domain.BaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 用户和角色关联 sys_project_role
 *
 * @author wangfeng
 */
@EqualsAndHashCode(callSuper = true)
@Api(value="角色表：项目和角色关联",tags="角色表：项目和角色关联")
@AllArgsConstructor
@Builder
@Data
public class SysProjectRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @ApiModelProperty(value = "角色ID")
    @Excel(name = "角色序号", cellType = Excel.ColumnType.NUMERIC)
    private Long roleId;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号")
    @Excel(name = "项目编号")
    private Long projectId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    @Excel(name = "角色名称")
    private String roleName;


    /** 角色类型 */
    @ApiModelProperty(value = "角色类型：1、项目代表；2、项目管理者；3、项目贡献者")
    @Excel(name = "角色类型：1、项目代表；2、项目管理者；3、项目贡献者")
    private Integer roleType;

    /** 角色状态（0正常 1停用） */
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    private Integer status = 0 ;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private Integer delFlag = 0;


    public SysProjectRole()
    {

    }

    public SysProjectRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleType);
    }

    public static boolean isAdmin(Integer roleType)
    {
        return roleType != null && 1 == roleType;
    }


    public Long getProjectId() {
        return projectId;
    }



    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

   @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roleId", getRoleId())
                .append("projectId", getProjectId())
                .append("roleName", getRoleName())
                .append("roleType", getRoleType())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
