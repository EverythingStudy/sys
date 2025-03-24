package cn.staitech.common.core.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 分页请求 可以用作父类，子类继承后增加自定义属性
 */
public class PageRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306405212144003406L;

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    @NotNull(message = "分页参数不能为空")
    private Integer pageNum;
    
    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    @NotNull(message = "分页参数不能为空")
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
