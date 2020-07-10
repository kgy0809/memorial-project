package com.memorial.greenroom.controller;

import com.memorial.mapper.DayMapper;
import com.memorial.pojo.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/7/2 10:46
 */
@Controller
@RequestMapping("day")
public class DayController {

    @Autowired
    private DayMapper dayMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String query(Map<String, Object> map) {
        List<Day> days = dayMapper.selectAll();
        map.put("day", days.get(0));
        return "system/day/day_update";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String update(Day day) {
        dayMapper.updateByPrimaryKeySelective(day);
        return "redirect:/day";
    }
}
