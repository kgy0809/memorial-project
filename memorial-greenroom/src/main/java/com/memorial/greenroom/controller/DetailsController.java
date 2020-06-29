package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.DetailsMapper;
import com.memorial.pojo.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 15:00
 */
@Controller
@RequestMapping("details")
public class DetailsController {

    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping
    public String details(){
        return "system/zuiyi/zhuiyi_list";
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
    public PageResultDTO<Details> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Details> details = detailsMapper.selectAll();
        PageInfo<Details> sysBannerPageInfo = new PageInfo<>(details);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), details);
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
        detailsMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/zuiyi/zhuiyi_add";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Details details) {
        if (details.getId() == null) {
            //新增
            details.setId(idWorker.nextId() + "");
            details.setTime(new Date());
            details.setTime2(new Date());
            detailsMapper.insert(details);
            return "redirect:/details";
        } else {
            detailsMapper.updateByPrimaryKeySelective(details);
            return "redirect:/details";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Details details = detailsMapper.selectByPrimaryKey(id);
        map.put("details",details);
        return "system/zuiyi/zhuiyi_update";
    }

}
