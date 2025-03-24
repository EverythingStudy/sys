package cn.staitech.job.service;

import cn.staitech.job.domain.SysImage;
import cn.staitech.job.mapper.FailImageDelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片上传失败后处理 实现层
 *
 * @author staitech
 */
@Service
public class FailImageDelServiceImpl implements FailImageDelService {

    @Autowired
    private FailImageDelMapper failImageDelMapper;
    /**
     * 删除上传失败的图像信息
     * @return 图像结果集
     */
    public List<SysImage> selectImageList(){
        return failImageDelMapper.selectImageList();
    };

    /**
     * 删除上传失败的图像信息
     * @param imageIdList
     */
    public void deleteImageByIdList(List imageIdList){
        failImageDelMapper.deleteImageByIdList(imageIdList);
    };
}
