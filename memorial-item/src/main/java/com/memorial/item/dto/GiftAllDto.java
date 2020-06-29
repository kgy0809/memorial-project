package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 10:52
 */
@Data
public class GiftAllDto {

    @ApiModelProperty("姓名")
    private List<String> name;
    @ApiModelProperty("鲜花一束 8 ")
    private List<GiftDto> list1;
    @ApiModelProperty("哀思花束  2 ")
    private List<GiftDto> list2;
    @ApiModelProperty("一炷香 2 ")
    private List<GiftDto> list3;
    @ApiModelProperty("香火不断 1 ")
    private List<GiftDto> list4;
    @ApiModelProperty("冥币1  3")
    private List<GiftDto> list5;
    @ApiModelProperty("冥币2  2 ")
    private List<GiftDto> list6;
    @ApiModelProperty("冥币3  2")
    private List<GiftDto> list7;
    @ApiModelProperty("蜡烛 4")
    private List<GiftDto> list8;
    @ApiModelProperty("祈祷红蜡 3 ")
    private List<GiftDto> list9;
    @ApiModelProperty("祈祷白蜡 3 ")
    private List<GiftDto> list10;
    @ApiModelProperty("饭菜 1 ")
    private List<GiftDto> list11;
    @ApiModelProperty("水果点心 1 ")
    private List<GiftDto> list12;
    @ApiModelProperty("香烟白酒 1 ")
    private List<GiftDto> list13;
    @ApiModelProperty("轿车 2 ")
    private List<GiftDto> list14;
    @ApiModelProperty("右 套餐 ")
    private List<GiftDto> list15;
    @ApiModelProperty("左 套餐 ")
    private List<GiftDto> list16;
    @ApiModelProperty("中 套餐 ")
    private List<GiftDto> list17;
}
