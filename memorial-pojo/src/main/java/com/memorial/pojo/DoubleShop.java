package com.memorial.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 12:54
 */
@Data
@Table(name = "memorial_double_shop")
public class DoubleShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String oneId;
    private Integer num;
    private String shopId;
}
