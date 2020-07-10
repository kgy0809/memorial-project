package com.memorial.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/15 16:58
 */
@Data

@Accessors(chain = true) // 链式编程写法
@AllArgsConstructor // 全参构造方法
@NoArgsConstructor // 无参构造方法
@Table(name = "memorial_ancestral_details")
public class Memorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("位置")
    private String site;
    @ApiModelProperty("简介")
    private String text;
    @Column(name = "user_id")
    private String userId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String state;
    @ApiModelProperty("出生日期1")
    private String date1;
    @ApiModelProperty("趋势日期1")
    private String trend1;
    @ApiModelProperty("出生日期2")
    private String date2;
    @ApiModelProperty("趋势日期2")
    private String trend2;

    @Transient
    private String loginName;

}
