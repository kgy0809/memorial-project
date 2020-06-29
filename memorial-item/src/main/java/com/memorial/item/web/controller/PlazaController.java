package com.memorial.item.web.controller;

import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.service.PlazaService;
import com.memorial.mapper.RelationMapper;
import com.memorial.pojo.Details;
import com.memorial.pojo.Plaza;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/13 17:05
 */
@RestController
@RequestMapping("plaza")
@Api(value = "广场", tags = "广场")
public class PlazaController {

    @Autowired
    private PlazaService plazaService;
    @Autowired
    private RelationMapper relationMapper;

    @GetMapping("/{page}/{rows}")
    @ApiOperation(value = "广场信息查询", notes = "广场信息查询", response = Plaza.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, value = "1", dataType = "int"),
            @ApiImplicitParam(name = "rows", required = true, value = "10", dataType = "int")
    })
    public PageResultDTO<Plaza> plazasList(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "rows") Integer rows
    ) {
        PageInfo<Plaza> pageInfo = plazaService.queryPlazasList(page, rows);
        return new PageResultDTO<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping
    @ApiOperation(value = "我要追忆", notes = "我要追忆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "拼接的字符串", dataType = "String"),
            @ApiImplicitParam(name = "text", required = true, value = "想对ta说：", dataType = "String"),
            @ApiImplicitParam(name = "id", required = true, value = "授权用户ID", dataType = "String")
    })
    public ResultDTO creationPlaza(
            @RequestParam("id") String id,
            @RequestParam("text") String text,
            @RequestParam("name") String name
    ) {
        plazaService.creationPlaza(name, text, id);
        return new ResultDTO(true, StatusCode.OK, "追忆成功");
    }

    @GetMapping("relation")
    @ApiOperation(value = "关系下拉查询", notes = "关系下拉查询")
    public ResultDTO relation() {
        return new ResultDTO(true, StatusCode.OK, "查询成功",relationMapper.selectAll());
    }

}
