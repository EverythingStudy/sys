package cn.staitech.job.service;

import cn.staitech.job.domain.SysImage;

import java.util.List;

/**
 * 图片上传失败后处理 服务层
 *
 * @author staitech
 */

public interface FailImageDelService {
    /**
     * 获取图像列表
     * @return 图像结果集
     */
    public List<SysImage> selectImageList();

    /**
     * 删除上传失败的图像信息
     * @param imageIdList
     */
    public void deleteImageByIdList(List imageIdList);
}
