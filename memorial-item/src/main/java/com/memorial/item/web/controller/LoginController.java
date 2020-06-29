package com.memorial.item.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.enums.ExceptionEnum;
import com.memorial.common.exception.LyException;
import com.memorial.common.utils.IdWorker;
import com.memorial.item.config.OpenIdProperties;
import com.memorial.item.utils.HttpClientUtil;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/30 19:01
 */
@RestController
@RequestMapping("login")
@Api(value = "用户授权", tags = "用户授权")
@Slf4j
public class LoginController {

    @Autowired
    private OpenIdProperties openIdProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdWorker idWorker;

    @GetMapping
    @ApiOperation(value = "授权登录", notes = "授权登录")
    public ResultDTO loginInit() throws UnsupportedEncodingException {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken)
        /*String backUrl = "http://zhuisiapi.j.brotop.cn/api/login/getWechatGZAccessToken";*/
        /*String backUrl = "https://zhuisi-h5.ioiogoo.cn/wapwx/#/login";*/
        String backUrl = "https://www.zhuisi5.com/wapwx/#/login";
        /*String backUrl = "http://zhuisionline.h.broteam.cn/#/login";*/
        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + openIdProperties.getAPPID() +
                "&redirect_uri=" + URLEncoder.encode(openIdProperties.getLoginUrl(), "UTF-8") +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        return new ResultDTO(true, StatusCode.OK, "授权成功 url 回调地址", url);
    }

    /**
     * 授权登录 回调
     * 信息保存
     *
     * @param code
     * @return
     */
    @GetMapping(value = "getWechatGZAccessToken")
    public ResultDTO loginAutho(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + openIdProperties.getAPPID() + "&secret=" + openIdProperties.getSECRET() + "&code=" + code + "&grant_type=authorization_code";

        String result = HttpClientUtil.doGet(url);
        //返回结果的json对象
        JSONObject jsonObject = JSON.parseObject(result);
        log.info("-------用户授权-------", jsonObject);
        String openid = "";
        if (result.indexOf("errcode") != -1) {
            throw new LyException(ExceptionEnum.WX_ERROR);
        } else {
            openid = jsonObject.get("openid").toString();
        }

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("openId", openid);
        User loginUser = userMapper.selectOneByExample(example);
        if (StringUtils.isEmpty(loginUser)) {
            /**
             * 不存在进行保存
             */
            //请求获取userInfo
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=" + jsonObject.getString("access_token") +
                    "&openid=" + jsonObject.getString("openid") +
                    "&lang=zh_CN";

            String resultInfo = HttpClientUtil.doGet(infoUrl);
            JSONObject user = JSON.parseObject(resultInfo);
            //需要传入 微信名字跟微信的头像
            loginUser = new User();
            loginUser.setId(idWorker.nextId() + "");
            loginUser.setTime(new Date());
            loginUser.setOpenId(openid);
            loginUser.setImg(user.get("headimgurl").toString());
            loginUser.setName(user.get("nickname").toString());
            userMapper.insert(loginUser);
        }
        String token = loginUser.getId();
        return new ResultDTO(true, StatusCode.OK, "登录成功", token);
    }
}
