package cn.staitech.system.mapper;


import cn.staitech.system.domain.SysProjectMenu;

import java.util.List;

public interface SysProjectMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysProjectMenu record);

    int insertSelective(SysProjectMenu record);

    SysProjectMenu selectByPrimaryKey(Long menuId);
    
    List<SysProjectMenu> selectList();

    int updateByPrimaryKeySelective(SysProjectMenu record);

    int updateByPrimaryKey(SysProjectMenu record);
}