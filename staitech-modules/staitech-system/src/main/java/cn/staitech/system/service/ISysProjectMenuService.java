package cn.staitech.system.service;

import cn.staitech.system.domain.SysProjectMenu;

import java.util.List;

public interface ISysProjectMenuService {
    
    List<SysProjectMenu> selectList();
    
    SysProjectMenu selectByPrimaryKey(Long menuId);
    
}
