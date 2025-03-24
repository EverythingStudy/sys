package cn.staitech.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @project: 
 * @description: 分页返回结果
 */
public class PageResponse<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private int pageNum;

    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    private int pageSize;

    /**
     * 记录总数
     */
    @ApiModelProperty("记录总数")
    private long total;

    /**
     * 数据内容
     */
    @ApiModelProperty("数据内容")
    private List<T> list;

    /**
     * 记录总数
     */
    @ApiModelProperty("总页数")
    private long pages;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}