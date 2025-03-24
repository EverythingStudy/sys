package cn.staitech.system.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author gjt.
 * @data 2023/6/2 16:20
 */
public class SysUserConstant {

    public static final Map<Long, String> SEX_MAP = new ImmutableMap.Builder<Long, String>()
            .put(0L, "男")
            .put(1L, "女")
            .build();
}
