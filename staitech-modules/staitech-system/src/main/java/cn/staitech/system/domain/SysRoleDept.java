package cn.staitech.system.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 角色和菜单关联 sys_role_dept
 * 
 * @author staitech
 */
@Api(value="角色和菜单关联",tags="角色和菜单关联")
public class SysRoleDept
{
    /** 角色ID */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    
    /** 部门ID */
    @ApiModelProperty(value = "部门ID")
    private Long deptId;


    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
