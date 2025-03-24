package cn.staitech.system.domain.user.out.data;

import cn.staitech.system.api.domain.SysUser;
import io.swagger.annotations.ApiModelProperty;


import java.util.Set;

public class UserInfoGetOut {
    @ApiModelProperty("用户数据")
    private SysUser user;
    @ApiModelProperty("角色数据")
    private Set<String> roles;
    @ApiModelProperty("权限数据")
    private Set<String> permissions;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {

        this.user = user;
        this.user.setPassword(null);
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
