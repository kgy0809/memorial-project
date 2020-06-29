package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 9:22
 */
@Data
public class NameAndRelationDto2 {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("关系")
    private String relation;
}
