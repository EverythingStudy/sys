package cn.staitech.job.task;

import cn.staitech.common.core.utils.SpringUtils;
import cn.staitech.job.domain.algorithm.ErisAlgorithm;
import cn.staitech.job.mapper.ErisAlgorithmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @Author wudi
 * @Date 2023/8/9 14:41
 * @desc 算法消费异常处理
 */
@Slf4j
@Component("algorithmCallBackTask")
public class AlgorithmCallBackTask {
    @Value("${algorithm.uuid}")
    private String algorithmUuid;

    ErisAlgorithmMapper erisAlgorithmMapper = SpringUtils.getBean(ErisAlgorithmMapper.class);

    public void algorithmCallBackTask() {
        log.info("算法消费异常处理批处理开始：");
        //监控效率
        StopWatch stopWatch = new StopWatch("算法消费异常处理");
        stopWatch.start("总效率");
        //查询算法处理表 状态+时间
        List<ErisAlgorithm> longs = erisAlgorithmMapper.queryAllByLimit();
        //发送消息
        if (!CollectionUtils.isEmpty(longs)) {
            for (ErisAlgorithm aLong : longs) {
                //修改关联表数据
                erisAlgorithmMapper.updateAlgorithm(aLong.getRelevanceId());
                //修改切片表数据状态
                if (algorithmUuid.equals(aLong.getAlgorithmUuid())) {
                    erisAlgorithmMapper.updateSpecialImage(aLong.getSpecialImageId());
                }
                //删除关联表数据
                //erisAlgorithmMapper.deleteAlgorithm(aLong.getRelevanceId());
            }
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

}
