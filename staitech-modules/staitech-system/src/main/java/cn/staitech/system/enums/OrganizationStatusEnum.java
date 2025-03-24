package cn.staitech.system.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gjt.
 * @data 2023/5/30 11:14
 */
public enum OrganizationStatusEnum {
    status_0(0L,"启用"),
    status_1(1L,"禁用");

    private Long value;
    private String label;
    OrganizationStatusEnum(Long value, String label) {
        this.label = label;
        this.value = value;
    }
    public static String getOrganizationByValue(Long value) {

        if (value == null) {
            return StringUtils.EMPTY;
        }
        for (OrganizationStatusEnum item : OrganizationStatusEnum.values()) {
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
