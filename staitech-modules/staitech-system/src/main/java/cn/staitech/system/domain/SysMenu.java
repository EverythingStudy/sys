package cn.staitech.system.domain;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.staitech.common.core.web.domain.BaseEntity;

/**
 * 菜单权限表 sys_menu
 * 
 * @author staitech
 */
@Api(value="菜单权限表",tags="菜单权限表")
public class SysMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /** 父菜单名称 */
    @ApiModelProperty(value = "父菜单名称")
    private String parentName;

    /** 父菜单ID */
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    /** 显示顺序 */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /** 路由地址 */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /** 组件路径 */
    @ApiModelProperty(value = "组件路径")
    private String component;

    /** 路由参数 */
    @ApiModelProperty(value = "路由参数")
    private String query;

    /** 是否为外链（0是 1否） */
    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private String isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）")
    private String isCache;

    /** 类型（M目录 C菜单 F按钮） */
    @ApiModelProperty(value = "类型（M目录 C菜单 F按钮）")
    private String menuType;

    /** 显示状态（0显示 1隐藏） */
    @ApiModelProperty(value = "显示状态（0显示 1隐藏）")
    private String visible;
    
    /** 菜单状态（0显示 1隐藏） */
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String status;

    /** 权限字符串 */
    @ApiModelProperty(value = "权限字符串")
    private String perms;

    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "功能模块标识 0:是功能模块；1：不是功能模块")
    private String isFunctionalModules;

    @ApiModelProperty(value = "路由名字（英文）")
    private String menuNameEn;
    @ApiModelProperty(value = " 该页面铺满横向布局，0:false;1:true")
    private String fullWidth;
    @ApiModelProperty(value = "该页面不需要填充整个页面，0:false;1:true")
    private String noFit;
    @ApiModelProperty(value = "该页面不需要app-header，0:false;1:true")
    private String noHeader;

    /** 子菜单 */
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public String getFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(String fullWidth) {
        this.fullWidth = fullWidth;
    }

    public String getNoFit() {
        return noFit;
    }

    public void setNoFit(String noFit) {
        this.noFit = noFit;
    }

    public String getNoHeader() {
        return noHeader;
    }

    public void setNoHeader(String noHeader) {
        this.noHeader = noHeader;
    }

    public String getMenuNameEn() {
        return menuNameEn;
    }

    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn;
    }

    public Long getMenuId()
    {
        return menuId;
    }

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    @NotBlank(message = "{SysMenu.menuName.notNull}")
    @Size(min = 0, max = 50, message = "{SysMenu.menuName.length}")
    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    @NotNull(message = "{SysMenu.orderNum.notNull}")
    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    @Size(min = 0, max = 200, message = "{SysMenu.path.length}")
    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @Size(min = 0, max = 200, message = "{SysMenu.component.length}")
    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public String getIsFrame()
    {
        return isFrame;
    }

    public void setIsFrame(String isFrame)
    {
        this.isFrame = isFrame;
    }

    public String getIsCache()
    {
        return isCache;
    }

    public void setIsCache(String isCache)
    {
        this.isCache = isCache;
    }

    @NotBlank(message = "{SysMenu.menuType.notNull}")
    public String getMenuType()
    {
        return menuType;
    }

    public void setMenuType(String menuType)
    {
        this.menuType = menuType;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Size(min = 0, max = 100, message = "{SysRoleInsertVO.roleKey.length}")
    public String getPerms()
    {
        return perms;
    }

    public void setPerms(String perms)
    {
        this.perms = perms;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public List<SysMenu> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysMenu> children)
    {
        this.children = children;
    }

    public String getIsFunctionalModules() {
        return isFunctionalModules;
    }

    public void setIsFunctionalModules(String isFunctionalModules) {
        this.isFunctionalModules = isFunctionalModules;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("menuId", getMenuId())
            .append("menuName", getMenuName())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("path", getPath())
            .append("component", getComponent())
            .append("isFrame", getIsFrame())
            .append("IsCache", getIsCache())
            .append("menuType", getMenuType())
            .append("visible", getVisible())
            .append("status ", getStatus())
            .append("perms", getPerms())
            .append("icon", getIcon())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
