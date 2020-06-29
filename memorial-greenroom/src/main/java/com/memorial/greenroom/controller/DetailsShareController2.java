package com.memorial.greenroom.controller;

import com.memorial.mapper.DetailsShareMapper;
import com.memorial.pojo.DetailsShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/5 15:19
 */
@Controller
@RequestMapping("detailsShare2")
public class DetailsShareController2 {

    @Autowired
    private DetailsShareMapper detailsShareMapper;

    @GetMapping
    public String queryByShare(Map<String, Object> map) {
        List<DetailsShare> shares = detailsShareMapper.selectAll();
        map.put("share",shares.get(2));
        return "system/detailsShare2/update_share";
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String updateShare(DetailsShare detailsShare){
        detailsShareMapper.updateByPrimaryKeySelective(detailsShare);
        return "redirect:/detailsShare2";
    }

}
