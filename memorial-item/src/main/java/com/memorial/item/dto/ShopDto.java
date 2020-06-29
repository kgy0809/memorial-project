package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/30 17:11
 */
@Data
public class ShopDto {
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("数量")
    private Integer num = 0;
}
