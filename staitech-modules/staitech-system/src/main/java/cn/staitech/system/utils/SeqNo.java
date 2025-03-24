package cn.staitech.system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class SeqNo {
    private static final String SEQ_KEY="serial_number:";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取redis流水5位
     *
     * @return 例：00001
     *
     */
    public String incr(String seqKey) {
        //serial_number为redis键
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(SEQ_KEY+seqKey, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.incrementAndGet();

        //STR_FORMAT 代表使用redis生成的流水位数，000代表三位数，当流水号大于999时自动从0开始
        final String STR_FORMAT = "00000";
        if (increment == 0) {
            increment = increment + 1;
        } else if (increment > 99999) {
            increment = 0L;
        }
        //位数不够，前面补0
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(increment);

    }
}