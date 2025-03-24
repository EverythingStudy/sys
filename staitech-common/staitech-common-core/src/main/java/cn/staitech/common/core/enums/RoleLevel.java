package cn.staitech.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description ：角色级别
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：RoleLevel
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/5/29 星期一 17:36
 */
public enum RoleLevel {
    SYSTEM_LEVEL(1, "系统级别"),
    SPECIAL_LEVEL(2, "专题级别");
    private final int code;
    private final String info;

    RoleLevel(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public static String getInfoByCode(Integer code) {
        if (code == null) {
            return StringUtils.EMPTY;
        }
        for (RoleLevel item : RoleLevel.values()) {
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
