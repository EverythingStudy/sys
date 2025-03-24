package cn.staitech.system.api.domain.document;

import cn.staitech.common.core.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = EsConsts.INDEX_OPER_LOG)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SysOperLogDoc {

    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    @Id
    @ApiModelProperty(value = "操作序号ID")
    @Excel(name = "操作序号", cellType = Excel.ColumnType.NUMERIC)
    private Long operId;

    /** 操作模块 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "一级菜单")
    @Excel(name = "一级菜单")
    private String menu;

    /** 操作模块 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "二级菜单")
    @Excel(name = "二级菜单")
    private String subMenu;

    /** 操作模块 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作模块")
    @Excel(name = "操作模块")
    private String title;


    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "业务类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据）")
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer businessType;

    /** 业务类型数组 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "业务类型数组")
    private Integer[] businessTypes;

    /** 请求方法 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "请求方法")
    @Excel(name = "请求方法")
    private String method;

    /** 请求方式 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "请求方式")
    @Excel(name = "请求方式")
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer operatorType;

    /** 操作人员 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作人员")
    @Excel(name = "操作人员")
    private String operName;


    /** 操作人员 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作人员ID")
    @Excel(name = "操作人员ID")
    private Long userId;

    /** 部门名称 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称")
    private String deptName;

    /** 请求url */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "请求地址")
    @Excel(name = "请求地址")
    private String operUrl;

    /** 操作地址 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作地址")
    @Excel(name = "操作地址")
    private String operIp;

    /** 请求参数 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "请求参数")
    @Excel(name = "请求参数")
    private String operParam;

    /** 返回参数 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "返回参数")
    @Excel(name = "返回参数")
    private String jsonResult;

    /** 项目ID */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "项目ID")
    @Excel(name = "项目ID", cellType = Excel.ColumnType.NUMERIC)
    private Long projectId;

    /** 切片ID */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "切片ID")
    @Excel(name = "切片ID", cellType = Excel.ColumnType.NUMERIC)
    private Long slideId;

    /** 操作状态（0正常 1异常） */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer status;

    /** 错误消息 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "错误消息")
    @Excel(name = "错误消息")
    private String errorMsg;

    /** 操作时间 */
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 更新前数据 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "更新前数据")
    @Excel(name = "更新前数据")
    private String beforeUpdating;

    /** 更新后数据 */
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "更新后数据")
    @Excel(name = "更新后数据")
    private String afterUpdating;

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
