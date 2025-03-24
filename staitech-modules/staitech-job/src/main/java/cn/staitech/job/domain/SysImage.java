package cn.staitech.job.domain;

import cn.staitech.common.core.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 图像表 tb_image
 */
@Data
public class SysImage extends BaseEntity {

    /**
     * 图像id
     */
    @ApiModelProperty(hidden = true)
    private Long imageId;

    /**
     * 图像名称
     */
    @ApiModelProperty(value = "文件名称（文件名）")
    private String imageName;

    /**
     * 图像url地址
     */
    @ApiModelProperty(hidden = true)
    private String imageUrl;

    /**
     * 图片绝对路径
     */
    @ApiModelProperty(hidden = true)
    private String imagePath;

    /**
     * 缩略图url地址
     */
    @ApiModelProperty(hidden = true)
    private String thumbUrl;

    /**
     * macro图片URL地址
     */
    @ApiModelProperty(hidden = true)
    private String macroUrl;

    /**
     * label图片URL地址
     */
    @ApiModelProperty(hidden = true)
    private String labelUrl;

    /**
     * 文件格式
     */
    @ApiModelProperty(hidden = true)
    private String format;

    /**
     * 宽度
     */
    @ApiModelProperty(hidden = true)
    private String width;

    /**
     * 高度
     */
    @ApiModelProperty(hidden = true)
    private String height;

    /**
     * 深度
     */
    @ApiModelProperty(hidden = true)
    private String depth;

    /**
     * 大小
     */
    @ApiModelProperty(hidden = true)
    private String size;

    /**
     * 大小
     */
    @ApiModelProperty(hidden = true)
    private String globalSize;

    /**
     * 分辨率
     */
    @ApiModelProperty(hidden = true)
    private String resolvingPower;

    /**
     * 每层的切片个数
     */
    @ApiModelProperty(hidden = true)
    private String tileCountList;

    /**
     * 总层数
     */
    @ApiModelProperty(hidden = true)
    private Integer levelCount;

    /**
     * 前端总切片个数
     */
    @ApiModelProperty(hidden = true)
    private Integer chunkTotal;

    /**
     * 图片的Md5值
     */
    @ApiModelProperty(hidden = true)
    private String md5;

    /**
     * x轴分辨率
     */
    @ApiModelProperty(hidden = true)
    private String resolutionX;

    /**
     * y轴分辨率
     */
    @ApiModelProperty(hidden = true)
    private String resolutionY;

    /**
     * 原放大倍数
     */
    @ApiModelProperty(hidden = true)
    private Integer sourceLens;

    /**
     * 图片更新状态(-1分片上传中，0分片合并及生成缩略图处理中，,1合并且生成缩略图（可显示）,2文件以经传输（不可见）)
     */
    @ApiModelProperty(hidden = true)
    private Integer processFlag;

    @ApiModelProperty(hidden = true)
    private String searchValue;

    @ApiModelProperty(hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Long updateBy;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private String remark;
}

