package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.common.utils.QiniuUtil;
import com.memorial.mapper.BannerMapper;
import com.memorial.mapper.BannerPlazaMapper;
import com.memorial.pojo.Banner;
import com.memorial.pojo.BannerPlaza;
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
@RequestMapping("banner1")
public class BannerController2 {

    @Autowired
    private BannerPlazaMapper bannerPlazaMapper;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping(method = RequestMethod.GET)
    public String banner() {
        return "system/banner1/list_banner";
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
    public PageResultDTO<BannerPlaza> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<BannerPlaza> bannerPlazas = bannerPlazaMapper.selectAll();
        PageInfo<BannerPlaza> sysBannerPageInfo = new PageInfo<>(bannerPlazas);
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
        bannerPlazaMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/banner1/add_banner";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(BannerPlaza bannerPlaza) {
        if (bannerPlaza.getId() == null) {
            //新增
            bannerPlaza.setId(idWorker.nextId() + "");
            bannerPlaza.setTime(new Date());
            bannerPlazaMapper.insert(bannerPlaza);
            return "redirect:/banner1";
        } else {
            bannerPlazaMapper.updateByPrimaryKeySelective(bannerPlaza);
            return "redirect:/banner1";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        BannerPlaza bannerPlaza = bannerPlazaMapper.selectByPrimaryKey(id);
        map.put("banner",bannerPlaza);
        return "system/banner1/update_banner";
    }

}
