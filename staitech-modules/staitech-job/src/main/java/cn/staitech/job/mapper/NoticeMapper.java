package cn.staitech.job.mapper;

import cn.staitech.job.domain.notice.out.Notice;
import cn.staitech.job.domain.notice.out.NoticeQueryOut;

import java.util.List;

/**
 * @Author wudi
 * @Date 2023/6/26 16:06
 * @desc
 */
public interface NoticeMapper {
    /**
     *
     * @param
     * @return 消息列表
     */
    List<NoticeQueryOut> selectList();

    /**
     *
     * @param list
     * @return 批量插入回收站消息条数
     */
    int insertBatch(List<Notice> list);

    /**
     *
     * @param noticeContent
     * @return 查询是否存在
     */
    int selectNotice(String noticeContent);
}
