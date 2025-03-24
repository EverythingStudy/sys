package cn.staitech.job.domain.document;

import cn.staitech.common.core.annotation.Excel;
import cn.staitech.system.api.domain.document.EsConsts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Document(indexName = EsConsts.INDEX_JOB_LOG)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SysJobLogDoc {
    /** ID */
    @Id
    @Excel(name = "日志序号")
    private Long jobLogId;

    /** 任务名称 */
    @Field(type = FieldType.Keyword)
    @Excel(name = "任务名称")
    private String jobName;

    /** 任务组名 */
    @Field(type = FieldType.Keyword)
    @Excel(name = "任务组名")
    private String jobGroup;

    /** 调用目标字符串 */
    @Field(type = FieldType.Keyword)
    @Excel(name = "调用目标字符串")
    private String invokeTarget;

    /** 日志信息 */
    @Field(type = FieldType.Keyword)
    @Excel(name = "日志信息")
    private String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Field(type = FieldType.Keyword)
    @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
    private String status;

    /** 异常信息 */
    @Field(type = FieldType.Keyword)
    @Excel(name = "异常信息")
    private String exceptionInfo;

    /** 开始时间 */
    @Field(type = FieldType.Keyword)
    private String createTime;


}
