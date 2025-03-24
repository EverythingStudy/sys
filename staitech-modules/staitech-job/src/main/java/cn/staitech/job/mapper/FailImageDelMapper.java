package cn.staitech.job.mapper;


import cn.staitech.job.domain.SysImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图片上传失败后处理 数据层
 *
 * @author staitech
 */
public interface FailImageDelMapper {
    /**
     * 删除上传失败的图像信息
     * @return 图像结果集
     */
    public List<SysImage> selectImageList();

    /**
     * 删除上传失败的图像信息
     * @param imageIdList
     */
    public void deleteImageByIdList(@Param("imageIdList") List imageIdList);
}
