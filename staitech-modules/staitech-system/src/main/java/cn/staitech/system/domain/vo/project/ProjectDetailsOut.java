package cn.staitech.system.domain.vo.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectDetailsOut {
    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /**
     * 图象数
     */
    @ApiModelProperty(value = "关联图象总数")
    private Long imageTotal;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;


    /**
     * 标签集id
     */
    @ApiModelProperty(value = "标签集id")
    private Long indicatorId;

    /**
     * 病理指标名称
     */
    @ApiModelProperty(value = "标签集名称")
    private String indicatorName;

    /**
     * 病理指标名称英文
     */
    @ApiModelProperty(value = "标签集名称英文")
    private String indicatorNameEn;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
    private Long createBy;

    /**
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty( value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间 update_time
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



    @ApiModelProperty(value = "项目状态")
    private Integer status;

    /**
     * 种属ID
     */
    @ApiModelProperty(value = "种属ID")
    private String speciesId;

    @ApiModelProperty(value = "种属名称")
    private String speciesName;

    @ApiModelProperty(value = "种属名称英文")
    private String speciesNameEn;

    @ApiModelProperty("染色类型（1RGB，2HEX）")
    private Integer colorType;

    /**
     * 品系ID
     */
    @ApiModelProperty("品系ID")
    private Integer productSeriesId;

    @ApiModelProperty(value = "品系名称")
    private String productSeries;

    @ApiModelProperty(value = "品系名称英文")
    private String productSeriesEn;

    /**
     * 项目类型:1标注2评审3标准训练集，4标注考核，6图像拼接，7算法预测，8医学评审
     */
    @ApiModelProperty("项目类型:1标注2评审3标准训练集，4标注考核，6图像拼接，7算法预测，8医学评审")
    private String projectType;

    @ApiModelProperty(value = "机构编号")
    private Long organizationId;


    @ApiModelProperty("是否已生成考题0-未生成；1-已生成")
    private String ifCreateQuestions;

    @ApiModelProperty(value = "算法模型id")
    private Long modelId;

    @ApiModelProperty(value = "病理组织id")
    private Long tissueId;

    @ApiModelProperty(value = "系统类型code")
    private Long systemCode;

    @ApiModelProperty(value = "脏器类型code")
    private Long viscusCode;

    @ApiModelProperty(value = "关联结构指标")
    private Long tagId;

    @ApiModelProperty(value = "切片数")
    private Integer slideTotal;

    @ApiModelProperty(value = "评审内容id")
    private Long contentId;

}
