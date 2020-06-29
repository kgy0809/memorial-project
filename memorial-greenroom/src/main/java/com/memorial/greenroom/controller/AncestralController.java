package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.mapper.MemorialMapper;
import com.memorial.mapper.PlazaMsgImgMapper;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:05
 */
@Controller
@RequestMapping("ancestral")
public class AncestralController {

    @Autowired
    private MemorialMapper memorialMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String order() {
        return "system/ancestral/ancestral_list";
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
    public PageResultDTO<Memorial> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Memorial.class);
        example.setOrderByClause(" time desc ");
        List<Memorial> memorials = memorialMapper.selectByExample(example);
        if (!memorials.isEmpty()) {
            for (Memorial memorial : memorials) {
                User user = userMapper.selectByPrimaryKey(memorial.getUserId());
                memorial.setLoginName(user.getName());
                StringBuffer stringBuffer = new StringBuffer();
                String[] split = memorial.getName().split("-");
                for (String s : split) {
                    String[] split1 = s.split(",");
                    stringBuffer.append(split1[0] + "-" + split1[1]).append(",");
                }
                memorial.setText(stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString());
            }
        }
        PageInfo<Memorial> sysBannerPageInfo = new PageInfo<>(memorials);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), sysBannerPageInfo.getList());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultDTO delete(@PathVariable("id") String id) {
        memorialMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查看详情
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, Map<String, Object> map) {
        Memorial memorial = memorialMapper.selectByPrimaryKey(id);
        map.put("memorial", memorial);
        return "system/ancestral/order_view";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Memorial memorial) {
        memorialMapper.updateByPrimaryKeySelective(memorial);
        return "redirect:/ancestral";

    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Memorial memorial = memorialMapper.selectByPrimaryKey(id);
        map.put("memorial", memorial);
        return "system/ancestral/ancestral_update";
    }

}
