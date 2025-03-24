package cn.staitech.common.core.utils;

import org.springframework.stereotype.Component;

@Component
public class SysRoleUtil {

    /**
     * 根据最新角色编号获取下一个角色编号
     *
     * @param roleSortLatest 最新角色编号
     * @return 下一个角色编号
     */
    public static String getSort(String roleSortLatest) {
        String letter = roleSortLatest.replaceAll("\\s*", "").replaceAll("[^(A-Za-z)]", "");
        String number = roleSortLatest.replaceAll("\\s*", "").replaceAll("[^(0-9)]", "");
        int i = Integer.parseInt(number);
        i++;
        String format = String.format("%02d", i);
        String roleSort = letter + format;
        return roleSort;
    }

    public static String getOrganizationCode(int i) {
        String format = String.format("%02d", i);
        String roleSort = "G" + format;
        return roleSort;
    }

    public static String getUserCode(int i) {
        String format = String.format("%04d", i);
        return format;
    }
}