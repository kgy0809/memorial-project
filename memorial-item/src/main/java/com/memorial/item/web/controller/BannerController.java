package com.memorial.item.web.controller;

import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.service.BannerService;
import com.memorial.pojo.Banner;
import com.memorial.pojo.DetailsShare;
import com.memorial.pojo.Music;
import com.memorial.pojo.Share;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 8:48
 */
@RestController
@RequestMapping("banner")
@Api(value = "首页轮播图", tags = "首页轮播图")
public class BannerController {

    @Autowired
    private BannerService bannerService;


    @GetMapping
    @ApiOperation(value = "追忆轮播查询", notes = "首页轮播查询",response = Banner.class)
    public ResultDTO bannerList(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.queryBannerList());
    }

    @GetMapping("square")
    @ApiOperation(value = "广场轮播查询", notes = "广场轮播查询",response = Banner.class)
    public ResultDTO banner1List(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.queryBanner1List());
    }

    @GetMapping("ancestralHall")
    @ApiOperation(value = "祠堂轮播查询", notes = "祠堂轮播查询",response = Banner.class)
    public ResultDTO banner2List(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.queryBanner2List());
    }


    @GetMapping("share")
    @ApiOperation(value = "统一分享", notes = "统一分享",response = Share.class)
    public ResultDTO shareList(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.queryShareList());
    }

    @GetMapping("details/share")
    @ApiOperation(value = "追思详情分享信息查询", notes = "追思详情首页分享信息查询",response = DetailsShare.class)
    public ResultDTO shareDetailsList(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.shareDetailsList());
    }

    @GetMapping("details/share1")
    @ApiOperation(value = "祠堂人物详情分享管理", notes = "祠堂人物详情分享管理",response = DetailsShare.class)
    public ResultDTO shareDetailsList1(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.shareDetailsList1());
    }

    @GetMapping("details/share2")
    @ApiOperation(value = "祠堂页页面分享管理", notes = "祠堂页页面分享管理",response = DetailsShare.class)
    public ResultDTO shareDetailsList2(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.shareDetailsList2());
    }

    @GetMapping("music")
    @ApiOperation(value = "背景音乐查询", notes = "背景音乐查询",response = Music.class)
    public ResultDTO musicList(){
        return new ResultDTO(true, StatusCode.OK,"查询成功",bannerService.queryMusicList());
    }
}
