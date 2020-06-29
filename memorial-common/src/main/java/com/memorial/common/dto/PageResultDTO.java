package com.memorial.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页返回结果
 */
@ApiModel("分页返回类")
public class PageResultDTO<T> {

    @ApiModelProperty("总记录数")
    private Long total;//总记录数
    @ApiModelProperty("当前页的数据列表")
    private List<T> rows;//当前页的数据列表

    public PageResultDTO(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}