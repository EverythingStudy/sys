package cn.staitech.job.mapper;

import cn.staitech.job.domain.algorithm.ErisAlgorithm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 *
 * @author : wd
 * @date : 2023-8-9
 */
public interface ErisAlgorithmMapper{



    List<ErisAlgorithm> queryAllByLimit();

    int deleteAlgorithm(Long relevance_id);
    int updateSpecialImage(Long specialImageId);
    int updateAlgorithm(Long relevance_id);

}