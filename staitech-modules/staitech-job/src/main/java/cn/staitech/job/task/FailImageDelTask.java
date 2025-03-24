package cn.staitech.job.task;

import cn.staitech.common.core.utils.SpringUtils;
import cn.staitech.job.constant.FailImageDelConstant;
import cn.staitech.job.domain.SysImage;
import cn.staitech.job.service.FailImageDelService;
import cn.staitech.job.service.FailImageDelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author staitech
 */
@Slf4j
@Component("failImageDelTask")
public class FailImageDelTask {
    /**
     * 删除上传失败的图像信息
     * @return 图像结果集
     */
    public void delImage() throws ParseException {

        FailImageDelService failImageDelService = SpringUtils.getBean(FailImageDelServiceImpl.class);

        List<SysImage> sysImageList = failImageDelService.selectImageList();
        List<Long> imageIdList = new ArrayList<>();
        List<String> imagePathList = new ArrayList<>();
        List<String> thumbPathList = new ArrayList<>();
        List<String> labelPathList = new ArrayList<>();
        List<String> macroPathList = new ArrayList<>();
        if (!sysImageList.isEmpty()) {
            for (SysImage sysImage : sysImageList) {
                // 判断图片上传时间，大于1天则进行处理
                Date imageCreatedTime = sysImage.getCreateTime();
                long daysBetween = getDateDiff(imageCreatedTime);
                if (daysBetween>=1){
                    imageIdList.add(sysImage.getImageId());
                    // 上传状态为-1的图像, 且图像、缩略图存储路径存在，添加到相应列表，进行批量删除
                    if (sysImage.getImagePath()!=null) {
                        imagePathList.add(sysImage.getImagePath());
                    }
                    if (sysImage.getThumbUrl()!=null) {
                        String thumbPath = getPath(sysImage.getThumbUrl());
                        thumbPathList.add(thumbPath);
                    }
                    if (sysImage.getLabelUrl()!=null) {
                        String labelPath = getPath(sysImage.getLabelUrl());
                        labelPathList.add(labelPath);
                    }
                    if (sysImage.getMacroUrl()!=null) {
                        String macroPath = getPath(sysImage.getMacroUrl());
                        macroPathList.add(macroPath);
                    }
                }
            }
        }
        // 进行批量删除
        if (!imagePathList.isEmpty()){imageFileDel(imagePathList);}
        if (!thumbPathList.isEmpty()){imageFileDel(thumbPathList);}
        if (!labelPathList.isEmpty()){imageFileDel(labelPathList);}
        if (!macroPathList.isEmpty()){imageFileDel(macroPathList);}
        if (!imageIdList.isEmpty()) {
            for (int i = 0; i < imageIdList.size(); i++) {
                imageSliceDel(imageIdList.get(i));
            }
            failImageDelService.deleteImageByIdList(imageIdList);
        };
        log.info("定时任务执行完毕，上传失败的图像表记录及存储文件删除成功");
    }



    /**
     * 获取thumb、label、macro的绝对存储路径
     * @return 绝对存储路径
     */
    private static String getPath(String url){
        //获取thumb、label、macro的绝对存储路径
        String[] pathArray = url.split("/");
        String localPath = FailImageDelConstant.localFilePath;
        for (int i = 0; i < pathArray.length-2; i++) {
            if(i>1){
                localPath = localPath+"/"+pathArray[i];
            }
        }
        return localPath;
    }

    /**
     * 删除image、thumb、label、macro的文件
     * @return 执行结果
     */
    private static void imageFileDel(List<String> pathList) {
        for (String path: pathList) {
            File imageFile = new File(path);
            if (imageFile.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(imageFile);
                    log.info(imageFile+"文件夹删除成功");
                } catch (IOException e) {
                    log.error(imageFile+"文件夹删除失败");
                    throw new RuntimeException(e);
                }
            }else if(imageFile.isFile()){
                try {
                    FileUtils.delete(imageFile);
                    log.info(imageFile+"文件删除成功");
                } catch (IOException e) {
                    log.error(imageFile+"文件删除失败");
                    throw new RuntimeException(e);
                }

            }
        }
    }


    /**
     * 删除图像切片文件的文件
     * @return 执行结果
     */
    private static void imageSliceDel(Long id) {
        String tempPath = FailImageDelConstant.localSlicePath+ "/" + id;
        File sliceFileDir = new File(tempPath);
        if (sliceFileDir.isDirectory()) {
            try {
                FileUtils.deleteDirectory(sliceFileDir);
                log.info(sliceFileDir+"文件夹删除成功");
            } catch (IOException e) {
                log.error(sliceFileDir+"文件夹删除失败");
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 获取两个日期之间的日期差（天）
     *
     * @param imageCreatedTime 图像上传创建时间
     * @return
     */
    public static long getDateDiff(Date imageCreatedTime) throws ParseException {
        // 获取系统当前时间
        Date cte = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cteSdf = sdf.format(cte);
        String imageCreatedTimeFormat = sdf.format(imageCreatedTime);
        // 计算日期差，单位：天
        Date startTime = sdf.parse(imageCreatedTimeFormat);
        Date endTime = sdf.parse(cteSdf);
        long daysBetween = (endTime.getTime() - startTime.getTime()) / (60 * 60 * 24 * 1000);
        return daysBetween;
    }



}
