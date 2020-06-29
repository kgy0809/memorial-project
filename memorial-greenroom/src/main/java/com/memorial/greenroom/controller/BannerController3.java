package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.common.utils.QiniuUtil;
import com.memorial.mapper.BannerAncestralMapper;
import com.memorial.mapper.BannerMapper;
import com.memorial.pojo.Banner;
import com.memorial.pojo.BannerAncestral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 14:34
 */
@Controller
@RequestMapping("banner2")
public class BannerController3 {

    @Autowired
    private BannerAncestralMapper bannerMapper;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping(method = RequestMethod.GET)
    public String banner() {
        return "system/banner2/list_banner";
    }

    /**
     * 查询全部
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public PageResultDTO<BannerAncestral> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<BannerAncestral> bannerAncestrals = bannerMapper.selectAll();
        PageInfo<BannerAncestral> sysBannerPageInfo = new PageInfo<>(bannerAncestrals);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), sysBannerPageInfo.getList());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResultDTO delete(@PathVariable("id") String id) {
        bannerMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/banner2/add_banner";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(BannerAncestral banner) {
        if (banner.getId() == null) {
            //新增
            banner.setId(idWorker.nextId() + "");
            banner.setTime(new Date());
            bannerMapper.insert(banner);
            return "redirect:/banner2";
        } else {
            bannerMapper.updateByPrimaryKeySelective(banner);
            return "redirect:/banner2";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        BannerAncestral bannerAncestral = bannerMapper.selectByPrimaryKey(id);
        map.put("banner",bannerAncestral);
        return "system/banner2/update_banner";
    }

}
