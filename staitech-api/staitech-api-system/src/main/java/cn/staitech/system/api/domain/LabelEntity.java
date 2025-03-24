package cn.staitech.system.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author wudi
 * @Date 2023/9/14 14:03
 * @desc
 */
@Data
public class LabelEntity {

    @ApiModelProperty("测试使用字段")
    private String source_image_path;

    @ApiModelProperty("图像上的点的坐标，格式为x, y")
    private List<List<Integer>> point;

    @ApiModelProperty("状态值，1:表示预加载图像，0:表示标注轮廓")
    private String status;

    @ApiModelProperty("当前图像的层级")
    private Integer tier;

    @ApiModelProperty("原坐标点")
    private List<Integer> start_location;

    @ApiModelProperty("右下角坐标")
    private List<Integer> end_location;

    @ApiModelProperty("当前的用户，例如admin")
    private String user;

    @ApiModelProperty("当前图像的ID")
    private String imageId;


}
