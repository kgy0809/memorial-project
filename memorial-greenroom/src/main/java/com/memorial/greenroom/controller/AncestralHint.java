package com.memorial.greenroom.controller;

import com.memorial.mapper.CpmMapper;
import com.memorial.pojo.Cpm;
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
 * @date 2020/5/27 15:37
 */
@Controller
@RequestMapping("ancestral/hint")
public class AncestralHint {

    @Autowired
    private CpmMapper cpmMapper;

    @GetMapping
    public String queryByShare(Map<String, Object> map) {
        List<Cpm> cpms = cpmMapper.selectAll();
        map.put("cpm",cpms.get(0));
        return "system/cpm/update_cpm";
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String updateShare(Cpm cpm){
        cpmMapper.updateByPrimaryKeySelective(cpm);
        return "redirect:/ancestral/hint";
    }
}
