package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.RelationMapper;
import com.memorial.pojo.Relation;
import com.memorial.pojo.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/27 17:44
 */
@Controller
@RequestMapping("relation")
public class RelationController {

    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping
    public String details(){
        return "system/relation/relation_list";
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
    public PageResultDTO<Relation> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Relation> relations = relationMapper.selectAll();
        PageInfo<Relation> sysBannerPageInfo = new PageInfo<>(relations);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), relations);
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
        relationMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/relation/relation_add";
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Relation relation) {
        if (relation.getId() == null) {
            //新增
            relation.setId(idWorker.nextId() + "");
            relationMapper.insert(relation);
            return "redirect:/relation";
        } else {
            relationMapper.updateByPrimaryKeySelective(relation);
            return "redirect:/relation";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Relation relation = relationMapper.selectByPrimaryKey(id);
        map.put("relation",relation);
        return "system/relation/relation_update";
    }
}
