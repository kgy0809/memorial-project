package com.memorial.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 17:29
 */
@Data
@Table(name = "memorial_relation")
public class Relation implements Serializable {

    private static final long serialVersionUID = 2722904742983522833L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
}
