package com.memorial.greenroom.controller;

import com.memorial.mapper.ShareMapper;
import com.memorial.pojo.Share;
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
@RequestMapping("share")
public class ShareController {

    @Autowired
    private ShareMapper shareMapper;

    @GetMapping
    public String queryByShare(Map<String, Object> map) {
        List<Share> shares = shareMapper.selectAll();
        map.put("share",shares.get(0));
        return "system/share/update_share";
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String updateShare(Share share){
        shareMapper.updateByPrimaryKeySelective(share);
        return "redirect:/share";
    }

}
