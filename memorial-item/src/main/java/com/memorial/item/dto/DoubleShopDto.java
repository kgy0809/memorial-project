package com.memorial.item.dto;

import com.memorial.pojo.Shop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/30 18:04
 */
@Data
public class DoubleShopDto {
    @ApiModelProperty("商品信息")
    private Shop shop;
    @ApiModelProperty("商品内容展示")
    private List<ShopDto> shopDtoList;
}
