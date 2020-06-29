package com.memorial.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/26 15:26
 */
@Data
@Table(name = "memorial_plaza_msg_img")
public class PlazaMsgImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String img;
}
