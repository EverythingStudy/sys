package cn.staitech.system.domain.user.out;

import cn.staitech.system.api.domain.SysRole;
import cn.staitech.system.api.domain.SysUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserQeuryOut {
    @ApiModelProperty("角色列表数据")
    private List<SysRole> roles;
    @ApiModelProperty("用户数据")
    private SysUser userInfo;
    @ApiModelProperty("角色")
    private List<Long> roleIds;

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public SysUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SysUser userInfo) {
        this.userInfo = userInfo;
        this.userInfo.setPassword(null);
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
