package com.memorial.item.dto;

import com.memorial.pojo.Details;
import com.memorial.pojo.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/30 16:53
 */
@Data
public class OrderDto {
    @ApiModelProperty("追忆者")
    private Details details;
    @ApiModelProperty("纪念薄")
    private List<Order> orders;
    @ApiModelProperty("礼品")
    private List<ShopDto> shopDtos;
    @ApiModelProperty("总数")
    private Integer giftSize;
}
