package cn.staitech.job.task;

import cn.staitech.common.core.utils.DateUtils;
import cn.staitech.common.core.utils.SpringUtils;
import cn.staitech.job.domain.notice.out.Notice;
import cn.staitech.job.domain.notice.out.NoticeQueryOut;
import cn.staitech.job.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wudi
 * @Date 2023/6/2 16:24
 * @desc 垃圾回收消息
 */
@Slf4j
@Component("specialReclaimNoticeTask")
public class SpecialReclaimNoticeTask {

    NoticeMapper noticeMapper = SpringUtils.getBean(NoticeMapper.class);

    public void createNotice() {
        log.info("专题回收站消息统计批量接口开始：");
        //创建响应
        List<NoticeQueryOut> resps = noticeMapper.selectList();
        if (!CollectionUtils.isEmpty(resps)) {
            Map<Long, List<NoticeQueryOut>> collect = resps.stream().collect(Collectors.groupingBy(NoticeQueryOut::getReclaimBy));
            List<Notice> reqList = new ArrayList<>();
            collect.forEach((k, v) -> {
                v.forEach(resp -> {
                    Notice notice = new Notice();
                    notice.setNoticeTitle("专题回收");
                    notice.setNoticeType("1");
                    notice.setStatus("0");
                    notice.setCreateTime(new Date());
                    notice.setRecipient(resp.getReclaimBy());
                    StringBuffer stringBuffer = new StringBuffer();
                    String dateStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, resp.getExpireTime());
                    stringBuffer.append("专题 (").append(resp.getSpecialNumber()).append(") 将于").append(dateStr).append("到期,请尽快到专题回收站处理");
                    notice.setNoticeContent(stringBuffer.toString());
                    //存在不重复添加
                    int i = noticeMapper.selectNotice(stringBuffer.toString());
                    if (i == 0) {
                        reqList.add(notice);
                    }
                });
            });
            if (!CollectionUtils.isEmpty(reqList)) {
                noticeMapper.insertBatch(reqList);
            }
        }
        log.info("专题回收站消息统计批量接口结束");
    }
}
