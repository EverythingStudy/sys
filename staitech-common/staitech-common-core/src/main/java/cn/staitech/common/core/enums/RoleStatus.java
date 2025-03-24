package cn.staitech.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description ：角色状态
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：RoleStatus
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/5/29 星期一 17:36
 */
public enum RoleStatus {
    OK(0, "启用"),
    DISABLE(1, "禁用");

    private final int code;
    private final String info;

    RoleStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public static String getInfoByCode(Integer code) {
        if (code == null) {
            return StringUtils.EMPTY;
        }
        for (RoleStatus item : RoleStatus.values()) {
            if (item.code == code) {
                return item.info;
            }
        }
        return StringUtils.EMPTY;
    }

    public int getCode() {
        return code;
    }
    public String getInfo() {
        return info;
    }
}
