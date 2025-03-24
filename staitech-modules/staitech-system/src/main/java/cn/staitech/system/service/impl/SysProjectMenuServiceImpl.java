package cn.staitech.system.service.impl;

import cn.staitech.system.domain.SysProjectMenu;
import cn.staitech.system.mapper.SysProjectMenuMapper;
import cn.staitech.system.service.ISysProjectMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangf
 */
@Service
public class SysProjectMenuServiceImpl implements ISysProjectMenuService {
    
    @Resource
    private SysProjectMenuMapper sysProjectMenumapper;
    
    
    @Override
    public List<SysProjectMenu> selectList() {
        return sysProjectMenumapper.selectList();
    }
    
    @Override
    public SysProjectMenu selectByPrimaryKey(Long menuId){
        return sysProjectMenumapper.selectByPrimaryKey(menuId);
    }
}
