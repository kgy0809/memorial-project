package com.memorial.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:19
 */
@Data
@Table(name = "memorial_plaza_msg")
public class Plaza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String userId;
    @ApiModelProperty(required = true)
    private String name;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("展示时间")
    private Date time;
    @ApiModelProperty("内容")
    private String text;

    @Transient
    @ApiModelProperty("用户头像")
    private String img;
    @Transient
    @ApiModelProperty("用户微信名字")
    private String loginName;
    @Transient
    @ApiModelProperty("祭奠人关系：父亲·李四")
    private List<String> list;
}
