package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.TemplateMapper;
import com.memorial.pojo.Details;
import com.memorial.pojo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 15:37
 */
@Controller
@RequestMapping("ancestral/template")
public class AncestralTemplate {

    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping
    public String details(){
        return "system/template/template_list";
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
    public PageResultDTO<Template> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Template> templates = templateMapper.selectAll();
        PageInfo<Template> sysBannerPageInfo = new PageInfo<>(templates);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), templates);
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
        templateMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/template/template_add";
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Template template) {
        if (template.getId() == null) {
            //新增
            template.setId(idWorker.nextId() + "");
            templateMapper.insert(template);
            return "redirect:/ancestral/template";
        } else {
            templateMapper.updateByPrimaryKeySelective(template);
            return "redirect:/ancestral/template";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Template template = templateMapper.selectByPrimaryKey(id);
        map.put("template",template);
        return "system/template/template_update";
    }
}
