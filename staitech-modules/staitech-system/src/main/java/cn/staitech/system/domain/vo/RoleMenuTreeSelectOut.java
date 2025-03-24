package cn.staitech.system.domain.vo;

import cn.staitech.system.domain.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author wudi
 * @Date 2023/9/21 14:39
 * @desc
 */
@Data
public class RoleMenuTreeSelectOut {
    @ApiModelProperty("所有菜单")
    private List<TreeSelect> menus;

    @ApiModelProperty("当前角色菜单")
    private List<Long> checkedKeys;

    @ApiModelProperty("所有功能模块")
    private List<SysMenu>  sysMenuList;
}
