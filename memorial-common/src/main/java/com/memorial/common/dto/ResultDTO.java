package com.memorial.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回结果实体类
 */
@ApiModel(value = "返回类")
public class ResultDTO {
    @ApiModelProperty("是否成功")
    private boolean flag;//是否成功
    @ApiModelProperty("返回码")
    private Integer code;//返回码
    @ApiModelProperty("返回信息")
    private String message;//返回信息
    @ApiModelProperty("返回数据")
    private Object data;//返回数据

    public ResultDTO(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultDTO(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
    //默认构造器
    public ResultDTO() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
