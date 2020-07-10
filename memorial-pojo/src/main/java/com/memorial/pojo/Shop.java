package com.memorial.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 12:42
 */
@Data
@Table(name = "memorial_shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ApiModelProperty("图片路径")
    private String img;
    @ApiModelProperty("名字")
    private String name;
    private Integer price;
    @ApiModelProperty("小时")
    private Integer hour;
    private Date time;
    @ApiModelProperty("1单品2套餐")
    private String state;
    @Transient
    @ApiModelProperty("价格")
    private String doublePrice;
    private Double weights;

    public void setPrice(Integer price) {
        this.doublePrice = price / 100 + "." + price % 100 / 10 + price % 100 % 10;
        this.price = price;
    }

}
