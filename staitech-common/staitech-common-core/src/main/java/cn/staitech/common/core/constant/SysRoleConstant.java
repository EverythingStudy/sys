package cn.staitech.common.core.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class SysRoleConstant {
    public static final String SYSTEM = "R01";
    public static final String SPECIAL = "S01";

    public final static boolean TRUE = true;
    public final static boolean FALSE = false;

    /**
     * 角色级别：0系统级别，1专题级别
     */
    public static final Map<Integer, String> ROLE_LEVEL = new ImmutableMap.Builder<Integer, String>()
            .put(0, "系统级别")
            .put(1, "机构管理员")
            .build();

    /**
     * 角色状态名称（0启用 1禁用）
     */
    public static final Map<String, String> ROLE_STATUS = new ImmutableMap.Builder<String, String>()
            .put("0", "启用")
            .put("1", "禁用")
            .build();
}
