package cn.staitech.system.domain.vo;

import cn.staitech.system.domain.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author wudi
 * @Date 2023/9/21 18:14
 * @desc
 */
@Data
public class MenuTreeByUserIdOut {
    @ApiModelProperty(value = "菜单下拉框数据")
    private List<SysMenu> menus;
    @ApiModelProperty(value = "功能模块列表")
    private List<SysMenu> moduleMenus;


}
