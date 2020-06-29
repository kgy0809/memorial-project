package com.memorial.item.web.controller;

import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.dto.WinXinEntity;
import com.memorial.item.utils.WXUnitl;
import com.memorial.item.utils.WeiXinUnitl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/weixin")
@Api(value = "分享参数", tags = "分享参数")
public class WeiXinShareController {

    @PostMapping
    @ApiOperation(value = "分享", notes = "分享")
    @ApiImplicitParam(name = "strUrl", required = true, value = "分享url", dataType = "String", paramType = "query")
    public ResultDTO share(HttpServletRequest request) {
        String strUrl = request.getParameter("strUrl");
        WinXinEntity wx = WeiXinUnitl.getWinXinEntity(strUrl);
        // 将wx的信息到给页面
        Map<String, Object> map = new HashMap<String, Object>();
        String sgture = WXUnitl.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), strUrl);
        map.put("signature", sgture.trim());//签名
        map.put("timestamp", wx.getTimestamp().trim());//时间戳
        map.put("noncestr", wx.getNoncestr().trim());//随即串
        map.put("appid", "wxb35ad73dc44d2754");//appID
        return new ResultDTO(true, StatusCode.OK, "查询成功", map);
    }

}