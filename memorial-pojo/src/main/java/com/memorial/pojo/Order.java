package com.memorial.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 12:55
 */
@Data
@Table(name = "memorial_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String shopId;
    @ApiModelProperty("寄语")
    private String text;
    private String userId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("展示时间")
    private Date time;
    private String state;
    private String detailsId;
    @Transient
    @ApiModelProperty("展示商品名字")
    private String shopName;
    @Transient
    @ApiModelProperty("展示商品图片")
    private String shopImg;
    @Transient
    @ApiModelProperty("展示购买用户昵称")
    private String userName;
    @Transient
    @ApiModelProperty("展示购买用户头像")
    private String userImg;

    @Transient
    private String openId;
    @Transient
    private Integer price;

}
