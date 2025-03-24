package cn.staitech.system.domain.vo;

import cn.staitech.common.core.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * 路由显示信息
 *
 * @author staitech
 */
public class MetaVo {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty(value = "置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    @ApiModelProperty(value = "设置该路由的图标，对应路径src/assets/icons/svg")
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    @ApiModelProperty(value = "设置为true，则不会被 <keep-alive>缓存")
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    @ApiModelProperty(value = "内链地址（http(s)://开头）")
    private String link;


    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "菜单父ID")
    private Long parentId;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = " 该页面铺满横向布局，默认false")
    private boolean fullWidth;
    @ApiModelProperty(value = "该页面不需要填充整个页面，默认false")
    private boolean noFit;
    @ApiModelProperty(value = "该页面不需要app-header，默认false")
    private boolean noHeader;

    public MetaVo() {
    }

    public MetaVo(String title, String icon, Long menuId, String perms) {
        this.title = title;
        this.icon = icon;
        this.menuId = menuId;
        this.perms = perms;
    }

    public MetaVo(String title, String icon, boolean noCache, Long menuId, String perms) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        this.menuId = menuId;
        this.perms = perms;
    }

    public MetaVo(String title, String icon, String link, Long menuId, String perms) {
        this.title = title;
        this.icon = icon;
        this.link = link;
        this.menuId = menuId;
        this.perms = perms;
    }

    public MetaVo(String title, String icon, boolean noCache, String link, Long menuId, String perms) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
        this.menuId = menuId;
        this.perms = perms;
    }

    public MetaVo(String title,Long parentId, String icon, boolean noCache, String link, Long menuId, String perms, boolean fullWidth, boolean noFit, boolean noHeader) {
        this.parentId=parentId;
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
        this.menuId = menuId;
        this.perms = perms;
        this.fullWidth = fullWidth;
        this.noFit = noFit;
        this.noHeader = noHeader;
    }
    public MetaVo(String title,Long parentId, String icon, Long menuId, String perms,boolean fullWidth,boolean noFit,boolean noHeader) {
        this.parentId=parentId;
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
        this.menuId = menuId;
        this.perms = perms;
        this.fullWidth=fullWidth;
        this.noFit=noFit;
        this.noHeader=noHeader;
    }
    public MetaVo(String title, Long parentId,String icon, String link, Long menuId, String perms,boolean fullWidth,boolean noFit,boolean noHeader) {
        this.parentId=parentId;
        this.title = title;
        this.icon = icon;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
        this.menuId = menuId;
        this.perms = perms;
        this.fullWidth=fullWidth;
        this.noFit=noFit;
        this.noHeader=noHeader;
    }

    public boolean isFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(boolean fullWidth) {
        this.fullWidth = fullWidth;
    }

    public boolean isNoFit() {
        return noFit;
    }

    public void setNoFit(boolean noFit) {
        this.noFit = noFit;
    }

    public boolean isNoHeader() {
        return noHeader;
    }

    public void setNoHeader(boolean noHeader) {
        this.noHeader = noHeader;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
