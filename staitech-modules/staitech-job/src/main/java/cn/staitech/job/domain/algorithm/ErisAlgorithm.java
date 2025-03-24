package cn.staitech.job.domain.algorithm;

import java.io.Serializable;

 /**
 * ;
 * @author : wd
 * @date : 2023-8-9
 */
public class ErisAlgorithm implements Serializable{
     private static final long serialVersionUID = 7301853515659642993L;
     /**  */
    private Long relevanceId;

    private Long specialImageId ;
    private String algorithmUuid;
    /**  */
     public Long getRelevanceId() {
         return relevanceId;
     }

     public void setRelevanceId(Long relevanceId) {
         this.relevanceId = relevanceId;
     }

     public Long getSpecialImageId() {
         return specialImageId;
     }

     public void setSpecialImageId(Long specialImageId) {
         this.specialImageId = specialImageId;
     }

     public String getAlgorithmUuid() {
         return algorithmUuid;
     }

     public void setAlgorithmUuid(String algorithmUuid) {
         this.algorithmUuid = algorithmUuid;
     }
 }