package com.memorial.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 10:50
 */
@Data
public class GiftDto {

    @ApiModelProperty("奉献人")
    private String name;
    @ApiModelProperty("剩余时间")
    private String time;
    @ApiModelProperty("图片Url")
    private String url;
    @ApiModelProperty("新加时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Long newTime;
    @ApiModelProperty("礼物名字")
    private String shopName;
}
