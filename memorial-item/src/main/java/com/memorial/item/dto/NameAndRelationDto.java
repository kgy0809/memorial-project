package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 9:22
 */
@Data
public class NameAndRelationDto {

    @ApiModelProperty("保存或者删除需要的ID")
    private String id;
    @ApiModelProperty("地点")
    private String site;
    @ApiModelProperty("生平")
    private String text;
    @ApiModelProperty("姓名跟关系")
    private List<NameAndRelationDto2> list;
    @ApiModelProperty("出生日期1")
    private String date1;
    @ApiModelProperty("趋势日期1")
    private String trend1;
    @ApiModelProperty("出生日期2")
    private String date2;
    @ApiModelProperty("趋势日期2")
    private String trend2;

}
