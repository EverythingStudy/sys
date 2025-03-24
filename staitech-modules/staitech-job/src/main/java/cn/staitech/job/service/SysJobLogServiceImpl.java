package cn.staitech.job.service;

import java.util.List;

import cn.staitech.common.core.utils.DateUtils;
import cn.staitech.common.core.utils.bean.BeanUtils;
import cn.staitech.job.domain.document.SysJobLogDoc;
import cn.staitech.job.elasticsearchRepositories.JobESRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.staitech.job.domain.SysJobLog;
import cn.staitech.job.mapper.SysJobLogMapper;


/**
 * 定时任务调度日志信息 服务层
 * 
 * @author staitech
 */
@Slf4j
@Service
public class SysJobLogServiceImpl implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Autowired
    private JobESRepository jobESRepository;

    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        SysJobLogDoc sysJobLogDoc=new SysJobLogDoc();
        BeanUtils.copyProperties(jobLog,sysJobLogDoc);
        sysJobLogDoc.setCreateTime(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss"));
        //写入es
        jobESRepository.save(sysJobLogDoc);
        //写入数据库
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
