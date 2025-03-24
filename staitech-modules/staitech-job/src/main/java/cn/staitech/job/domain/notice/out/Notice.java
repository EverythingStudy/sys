package cn.staitech.job.domain.notice.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author wudi
 * @Date 2023/6/26 16:27
 * @desc 消息实体类
 */
@Data
public class Notice {

    private Long noticeId ;

    @ApiModelProperty(name = "公告标题",notes = "")
    private String noticeTitle ;

    @ApiModelProperty(name = "公告类型（1消息 2公告）",notes = "")
    private String noticeType ;

    @ApiModelProperty(name = "消息内容",notes = "")
    private String noticeContent ;

    @ApiModelProperty(name = "消息状态（0未读 1已读）",notes = "")
    private String status ;

    @ApiModelProperty(name = "消息接收者",notes = "")
    private Long recipient ;

    @ApiModelProperty(name = "创建者",notes = "")
    private Long createBy ;

    @ApiModelProperty(name = "创建时间",notes = "")
    private Date createTime ;

    @ApiModelProperty(name = "更新者",notes = "")
    private Long updateBy ;

    @ApiModelProperty(name = "更新时间",notes = "")
    private Date updateTime ;

    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
}
