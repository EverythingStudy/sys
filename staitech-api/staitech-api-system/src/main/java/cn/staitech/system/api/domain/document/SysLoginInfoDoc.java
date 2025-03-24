package cn.staitech.system.api.domain.document;

import cn.staitech.common.core.annotation.Excel;
import cn.staitech.common.core.annotation.Excel.ColumnType;
import cn.staitech.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * 系统访问记录表
 * 
 * @author staitech
 */
@Document(indexName = EsConsts.INDEX_LOGIN_LOG)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysLoginInfoDoc
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @ApiModelProperty(value = "序号")
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /** 用户账号 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "用户账号")
    @Excel(name = "用户账号")
    private String userName;

    /** 状态 0成功 1失败 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "状态 0成功 1失败")
    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /** 地址 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "地址")
    @Excel(name = "地址")
    private String ipaddr;

    /** 描述 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "描述")
    @Excel(name = "描述")
    private String msg;

    /** 访问时间 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "浏览器信息")
    @Excel(name = "浏览器信息", width = 30)
    private String browser;

    /** 访问时间 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "访问时间")
    @Excel(name = "访问时间", width = 30)
    private String accessTime;

    /** 创建时间 */
    @Field(type = FieldType.Long)
    @ApiModelProperty(value = "创建时间")
    private long time;

}