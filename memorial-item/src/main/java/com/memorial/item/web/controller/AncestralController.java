package com.memorial.item.web.controller;

import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.dto.*;
import com.memorial.item.service.MemorialService;
import com.memorial.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/15 16:56
 */
@RestController
@RequestMapping("ancestral")
@Api(value = "祠堂模块", tags = "祠堂模块")
public class AncestralController {

    @Autowired
    private MemorialService memorialService;

    @PostMapping
    @ApiOperation(value = "纪念馆信息查询", notes = "纪念馆信息查询", response = Details.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, value = "1", dataType = "int"),
            @ApiImplicitParam(name = "rows", required = true, value = "10", dataType = "int"),
            @ApiImplicitParam(name = "userId", required = true, value = "用户ID", dataType = "String"),
            @ApiImplicitParam(name = "type", required = true, value = "1所有 2我的 3首页查询", dataType = "String"),
            @ApiImplicitParam(name = "site", required = false, value = "地址筛选", dataType = "String"),
            @ApiImplicitParam(name = "name", required = false, value = "名字搜索", dataType = "String"),
    })
    public PageResultDTO<MemorialDto> detailsList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "rows") Integer rows,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "site", required = false) String site

    ) {
        PageInfo<MemorialDto> memorialDtoPageInfo = memorialService.queryList(page, rows, type, name, site, userId);
        return new PageResultDTO<>(memorialDtoPageInfo.getTotal(), memorialDtoPageInfo.getList());
    }

    @PostMapping("creation")
    @ApiOperation(value = "创建纪念馆", notes = "创建纪念馆", response = Details.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "字符串拼接：", dataType = "String"),
            @ApiImplicitParam(name = "site", required = true, value = "ta出生于：：", dataType = "String"),
            @ApiImplicitParam(name = "text", required = true, value = "想对ta说：", dataType = "String"),
            @ApiImplicitParam(name = "userId", required = true, value = "创建人ID", dataType = "String"),
            @ApiImplicitParam(name = "dateOfBirth1", required = false, value = "出生日期1", dataType = "String"),
            @ApiImplicitParam(name = "trendDate1", required = false, value = "趋势日期1", dataType = "String"),
            @ApiImplicitParam(name = "dateOfBirth2", required = false, value = "出生日期2", dataType = "String"),
            @ApiImplicitParam(name = "trendDate2", required = false, value = "趋势日期2", dataType = "String")
    })
    public ResultDTO creationMemorial(
            @RequestParam("name") String name,
            @RequestParam("site") String site,
            @RequestParam("text") String text,
            @RequestParam("userId") String userId,
            @RequestParam("dateOfBirth1") String dateOfBirth1,
            @RequestParam("trendDate1") String trendDate1,
            @RequestParam("dateOfBirth2") String dateOfBirth2,
            @RequestParam("trendDate2") String trendDate2
    ) {
        String s = memorialService.creationMemorial(name, site, text, userId, dateOfBirth1, trendDate1, dateOfBirth2, trendDate2);
        return new ResultDTO(true, StatusCode.OK, "创建成功",s);
    }

    @GetMapping("cpm")
    @ApiOperation(value = "创建纪念馆弹窗 提示", notes = "创建纪念馆弹窗 提示", response = Cpm.class)
    public ResultDTO cpmMemorial() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", memorialService.cpmMemorial());
    }

    @GetMapping("details/{id}")
    @ApiOperation(value = "详情信息", notes = "详情信息", response = MemorialDto2.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "祠堂ID", dataType = "String")
    })
    public ResultDTO queryById(@PathVariable("id") String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", memorialService.queryById(id));
    }

    @GetMapping("update/{id}")
    @ApiOperation(value = "纪念馆编辑", notes = "纪念馆编辑", response = NameAndRelationDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "祠堂ID", dataType = "String")
    })
    public ResultDTO updateById(@PathVariable("id") String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", memorialService.updateById(id));
    }

    @PutMapping("creation")
    @ApiOperation(value = "修改纪念馆", notes = "修改纪念馆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "字符串拼接：", dataType = "String"),
            @ApiImplicitParam(name = "site", required = true, value = "ta出生于：：", dataType = "String"),
            @ApiImplicitParam(name = "text", required = true, value = "想对ta说：", dataType = "String"),
            @ApiImplicitParam(name = "Id", required = true, value = "需要修改的ID", dataType = "String"),
            @ApiImplicitParam(name = "dateOfBirth1", required = false, value = "出生日期1", dataType = "String"),
            @ApiImplicitParam(name = "trendDate1", required = false, value = "趋势日期1", dataType = "String"),
            @ApiImplicitParam(name = "dateOfBirth2", required = false, value = "出生日期2", dataType = "String"),
            @ApiImplicitParam(name = "trendDate2", required = false, value = "趋势日期2", dataType = "String")
    })
    public ResultDTO updateMemorial(
            @RequestParam("name") String name,
            @RequestParam("site") String site,
            @RequestParam("text") String text,
            @RequestParam("id") String id,
            @RequestParam("dateOfBirth1") String dateOfBirth1,
            @RequestParam("trendDate1") String trendDate1,
            @RequestParam("dateOfBirth2") String dateOfBirth2,
            @RequestParam("trendDate2") String trendDate2
    ) {
        memorialService.updateMemorial(name, site, text, id,dateOfBirth1,trendDate1,dateOfBirth2,trendDate2);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除纪念馆", notes = "删除纪念馆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "祠堂ID", dataType = "String")
    })
    public ResultDTO deleteById(@PathVariable("id") String id) {
        memorialService.deleteById(id);
        return new ResultDTO(true,StatusCode.OK,"删除成功");
    }

    @GetMapping("template")
    @ApiOperation(value = "模板查询", notes = "模板查询",response = Template.class)
    public ResultDTO getTemplate(){
        return new ResultDTO(true,StatusCode.OK,"查询成功",memorialService.getTemplate());
    }

    @GetMapping("look/clan/{id}")
    @ApiOperation(value = "查看祠堂", notes = "查看祠堂",response = GiftAllDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "祠堂ID", dataType = "String")
    })
    public ResultDTO lookClanById(@PathVariable("id") String id){
        return new ResultDTO(true,StatusCode.OK,"查询成功",memorialService.lookClanById(id));
    }

    @GetMapping("look/book/{id}/{page}/{rows}")
    @ApiOperation(value = "查看记录册", notes = "查看记录册",response = Order.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "祠堂ID", dataType = "String"),
            @ApiImplicitParam(name = "page", required = true, value = "1", dataType = "int"),
            @ApiImplicitParam(name = "rows", required = true, value = "10", dataType = "int")
    })
    public PageResultDTO<Order> lookBookById(
            @PathVariable("id") String id,
            @PathVariable("page")Integer page,
            @PathVariable("rows")Integer rows
    ){
        PageInfo<Order> orderPageInfo = memorialService.lookBookById(id, page, rows);
        return new PageResultDTO<>(orderPageInfo.getTotal(),orderPageInfo.getList());
    }
}

