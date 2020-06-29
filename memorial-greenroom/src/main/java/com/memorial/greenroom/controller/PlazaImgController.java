package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.PlazaMapper;
import com.memorial.mapper.PlazaMsgImgMapper;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.Banner;
import com.memorial.pojo.Plaza;
import com.memorial.pojo.PlazaMsgImg;
import com.memorial.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:05
 */
@Controller
@RequestMapping("plaza/img")
public class PlazaImgController {

    @Autowired
    private PlazaMsgImgMapper plazaMsgImgMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String plaza(Map<String, Object> map) {
        List<PlazaMsgImg> plazaMsgImgs = plazaMsgImgMapper.selectAll();
        map.put("plaza", plazaMsgImgs.get(0));
        return "system/plaza_img/list_banner";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(PlazaMsgImg plazaMsgImg) {
        plazaMsgImgMapper.updateByPrimaryKeySelective(plazaMsgImg);
        return "redirect:/plaza/img";

    }

}
