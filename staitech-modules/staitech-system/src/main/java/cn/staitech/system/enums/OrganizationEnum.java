package cn.staitech.system.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gjt.
 * @data 2023/5/24 9:17
 */
public enum OrganizationEnum {
    DEL_FLAG_0(0L, "存在"),
    DEL_FLAG_2(2L, "删除");


    private Long value;
    private String label;

    OrganizationEnum(Long value, String label) {
        this.label = label;
        this.value = value;
    }

    public static String getOrganizationByValue(Long value) {

        if (value == null) {
            return StringUtils.EMPTY;
        }
        for (OrganizationEnum item : OrganizationEnum.values()) {
            if (item.value.equals(value)) {
                return item.label;
            }
        }
        return StringUtils.EMPTY;
    }


    public Long value() {
        return value;
    }

    public String label() {
        return label;
    }
}
