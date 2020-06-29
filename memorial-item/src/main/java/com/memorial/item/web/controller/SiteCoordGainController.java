package com.memorial.item.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.utils.HttpClientUtil;
import com.memorial.pojo.Plaza;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/6/5 14:56
 */
@RestController
@RequestMapping("gain")
@Api(value = "获取坐标接口", tags = "获取坐标接口")
public class SiteCoordGainController {

    @Value("${yhbr.openid.key}")
    private String key;

    @GetMapping("/{location}")
    @ApiOperation(value = "获取坐标", notes = "获取坐标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", required = true, value = "金纬度", dataType = "String")
    })
    public ResultDTO getGain(@PathVariable("location") String location) {
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + location + "&key=" + key;
        String post = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(post);
        String result = jsonObject.getString("result");
        String ad_info = JSON.parseObject(result).getString("ad_info");
        return new ResultDTO(true, StatusCode.OK, "获取成功",JSON.parseObject(ad_info));
    }
}
