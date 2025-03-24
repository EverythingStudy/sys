package cn.staitech.system.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Description ：
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：SpecialRole
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/6/14 星期三 10:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialRole implements Serializable {
    private static final long serialVersionUID = -8935393189427109335L;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 专题ID
     */
    private Long specialId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    private Long menuId;

    private String perms;

    /**
     * 权限列表
     */
    private Set<String> specialPermissions;
}
