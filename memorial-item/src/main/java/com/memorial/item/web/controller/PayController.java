package com.memorial.item.web.controller;

import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.service.OrderService;
import com.memorial.item.utils.sdk.WXPayUtil;
import com.memorial.mapper.OrderMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/5 14:03
 */
@RestController
@RequestMapping("pay")
@Api(value = "支付", tags = "购买支付")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;


    /**
     * 购买
     * @param id
     * @param shopId
     * @return
     */
    @PostMapping("create")
    @ApiOperation(value = "购买",notes = "购买")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", required = true,value = "用户id",dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "shopId", required = true, value = "商品ID",dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "detailsId", required = true, value = "追忆ID",dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "text", required = false, value = "信息描述",dataType = "String")
    })
    public ResultDTO createOrder(
            @RequestBody@RequestParam("id") String id,
            @RequestBody@RequestParam("shopId") String shopId,
            @RequestBody@RequestParam(name = "text",required = false) String text,
            @RequestBody@RequestParam("detailsId") String detailsId
    ){
        return new ResultDTO(true, StatusCode.OK,"购买成功",orderService.createOrder(id,shopId,text,detailsId));
    }





    /**
     * 订单回调
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = false;
        ServletInputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = request.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            StringBuilder stb = new StringBuilder();
            String s = "";
            while ((s = br.readLine()) != null) {
                stb.append(s);
            }
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(stb.toString());//将微信发的xml转map
            log.info("----支付回调----",notifyMap);
            if (notifyMap.get("return_code").equals("SUCCESS") || notifyMap.get("return_code").equals("01")) {    //返回成功
                if (notifyMap.get("result_code").equals("SUCCESS") || notifyMap.get("result_code").equals("01")) {
                    String out_trade_no = notifyMap.get("out_trade_no");//商户订单号
                    //处理订单
                    if (StringUtils.isNotBlank(out_trade_no)) {
                        //修改支付状态为已支支付                 appOrderServiceImpl.updateC(out_trade_no);
                        if ("1".equals(orderService.queryByOrderId(out_trade_no).getState())) {
                            orderService.updateOrderState(out_trade_no);
                        }
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            flag = false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                flag = true;
            }
        }
        if (flag) {
            response.getWriter().println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
        } else {
            response.getWriter().println("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[return_code_err]]></return_msg></xml>");
        }
    }
}
