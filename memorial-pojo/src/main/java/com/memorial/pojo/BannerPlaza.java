package com.memorial.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/6/17 16:27
 */
@Data
@Table(name = "memorial_banner_plaza")
public class BannerPlaza implements Serializable {
    private static final long serialVersionUID = -46717040178800589L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ApiModelProperty("首页轮播图片路径")
    private String img;
    private Date time;
}
