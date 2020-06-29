package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/15 17:54
 */
@Data
public class MemorialDto2 {

    @ApiModelProperty("祠堂ID")
    private String id;
    @ApiModelProperty("姓名")
    private List<String> name;
    @ApiModelProperty("地址")
    private String site;
    @ApiModelProperty("详情")
    private String text;
    @ApiModelProperty("数量")
    private Integer shopSize;
    @ApiModelProperty("展示logo")
    private String img;
    @ApiModelProperty("礼品")
    private List<ShopDto> shopDtos;
    @ApiModelProperty("出生日期1")
    private String date1;
    @ApiModelProperty("趋势日期1")
    private String trend1;
    @ApiModelProperty("出生日期2")
    private String date2;
    @ApiModelProperty("趋势日期2")
    private String trend2;

}
