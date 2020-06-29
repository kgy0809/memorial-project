package com.memorial.item.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
