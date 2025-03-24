package cn.staitech.system.service;

import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.system.api.domain.SysLogininfor;
import cn.staitech.system.api.domain.document.SysLoginInfoDoc;
import cn.staitech.system.api.domain.document.SysOperLogDoc;

import java.util.List;
import java.util.Map;

public interface ILogService {

    TableDataInfo queryLoginLogByPage(Map params) throws Exception;

    List<SysLoginInfoDoc> queryLoginLog(Map params) throws Exception;

    TableDataInfo queryOperLogByPage(Map params) throws Exception;

    List<SysOperLogDoc> queryOperLog(Map params) throws Exception;
}
