package com.memorial.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/5 15:20
 */
@Data
@Table(name = "memorial_details_share")
public class DetailsShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty("分享图片地址")
    private String img;
    @ApiModelProperty("分享标题")
    private String title;
    @ApiModelProperty("分享内容")
    private String content;
}
