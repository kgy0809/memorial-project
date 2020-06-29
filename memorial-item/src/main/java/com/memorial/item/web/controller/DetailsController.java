package com.memorial.item.web.controller;

import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.item.dto.DoubleShopDto;
import com.memorial.item.dto.OrderDto;
import com.memorial.item.service.DetailsService;
import com.memorial.pojo.Details;
import com.memorial.pojo.Order;
import com.memorial.pojo.Shop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 9:47
 */
@RestController
@RequestMapping("index")
@Api(value = "首页信息展示", tags = "首页信息展示")
public class DetailsController {

    @Autowired
    private DetailsService detailsService;

    @GetMapping("{page}/{rows}")
    @ApiOperation(value = "追忆信息", notes = "追忆信息", response = Details.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, value = "1", dataType = "int"),
            @ApiImplicitParam(name = "rows", required = true, value = "10", dataType = "int")
    })
    public PageResultDTO<Details> detailsList(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "rows") Integer rows
    ) {
        PageInfo<Details> pageInfo = detailsService.queryDetailsList(page, rows);
        return new PageResultDTO<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping("details/{id}")
    @ApiOperation(value = "追忆详情信息", notes = "追忆详情信息", response = OrderDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "追忆ID", dataType = "int")
    })
    public ResultDTO queryById(@PathVariable("id") String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", detailsService.queryById(id));
    }

    @GetMapping("details/{page}/{rows}/{id}")
    @ApiOperation(value = "纪念薄查询", notes = "纪念薄查询", response = Order.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "追忆ID", dataType = "int"),
            @ApiImplicitParam(name = "page", required = true, value = "1", dataType = "int"),
            @ApiImplicitParam(name = "rows", required = true, value = "20", dataType = "int")
    })
    public PageResultDTO<Order> queryByIdDetails(
            @PathVariable("page") Integer page,
            @PathVariable("rows") Integer rows,
            @PathVariable("id") String id
    ) {
        PageInfo<Order> orderPageInfo = detailsService.queryByIdDetails(page, rows, id);
        return new PageResultDTO<>(orderPageInfo.getTotal(), orderPageInfo.getList());
    }




    @GetMapping("shop/{type}")
    @ApiOperation(value = "我要祭祀查询", notes = "我要祭祀查询", response = Shop.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", required = true, value = "type=1查询单品，type=2查询套餐", dataType = "String")
    })
    public ResultDTO queryShop(@PathVariable("type") String type) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", detailsService.queryShop(type));
    }

    @GetMapping("shop/meal/{id}")
    @ApiOperation(value = "套餐详情查询", notes = "套餐详情查询", response = DoubleShopDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "套餐查询ID", dataType = "String")
    })
    public ResultDTO queryShopById(@PathVariable("id") String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", detailsService.queryShopById(id));
    }

}
