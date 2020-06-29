package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.common.utils.QiniuUtil;
import com.memorial.mapper.BannerMapper;
import com.memorial.pojo.Banner;
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
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private IdWorker idWorker;

    @Value("${yhbr.openid.ACCESSKEY}")
    private String ACCESSKEY;
    @Value("${yhbr.openid.SECRETKEY}")
    private String SECRETKEY;
    @Value("${yhbr.openid.BUCKETNAME}")
    private String BUCKETNAME;
    @Value("${yhbr.openid.FILEDOMAIN}")
    private String FILEDOMAIN;

    @RequestMapping(method = RequestMethod.GET)
    public String banner() {
        return "system/banner/list_banner";
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
    public PageResultDTO<Banner> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Banner> sysBanners = bannerMapper.selectAll();
        PageInfo<Banner> sysBannerPageInfo = new PageInfo<>(sysBanners);
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
        return "system/banner/add_banner";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Banner banner) {
        if (banner.getId() == null) {
            //新增
            banner.setId(idWorker.nextId() + "");
            banner.setTime(new Date());
            bannerMapper.insert(banner);
            return "redirect:/banner";
        } else {
            bannerMapper.updateByPrimaryKeySelective(banner);
            return "redirect:/banner";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        map.put("banner",banner);
        return "system/banner/update_banner";
    }

    /**
     * 新增轮播图
     *
     * @param file
     * @return
     */
    @PostMapping("insert")
    @ResponseBody
    public Map<String, Object> updateImage(@RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        if (file.getSize() == 0) {
            map.put("msg", "请添加图片！");
            map.put("code", 500);
        }
        String url = addImage(file);
        map.put("src", url);
        return map;
    }

    /**
     * 轮播图上传到七牛云
     * @param file
     * @return
     */
    public String addImage(MultipartFile file) {
        try {
            return new QiniuUtil(ACCESSKEY,SECRETKEY,BUCKETNAME,FILEDOMAIN).upload(UUID.randomUUID().toString().replace("-",""), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
