package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.common.utils.IdWorker;
import com.memorial.mapper.DoubleShopMapper;
import com.memorial.mapper.ShopMapper;
import com.memorial.pojo.Banner;
import com.memorial.pojo.DoubleShop;
import com.memorial.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 16:10
 */
@Controller
@RequestMapping("shop")
public class ShopController {

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DoubleShopMapper doubleShopMapper;

    @RequestMapping
    public String shop() {
        return "system/shop/shop_list";
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
    public PageResultDTO<Shop> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Shop.class);
        example.setOrderByClause(" weights desc ");
        List<Shop> shops = shopMapper.selectByExample(example);
        PageInfo<Shop> sysBannerPageInfo = new PageInfo<>(shops);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), shops);
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
        shopMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping("/add")
    public String add() {
        return "system/shop/shop_add";
    }

    @RequestMapping("/double/add")
    public String doubleAdd(Map<String, Object> map) {
        Example example = new Example(Shop.class);
        example.createCriteria().andEqualTo("state", "1");
        List<Shop> shops = shopMapper.selectByExample(example);
        map.put("shop", shops);
        return "system/shop/shop_double_add";
    }

    /**
     * 保存
     * @param shop
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Shop shop,Double price1) {
        if (shop.getId() == null) {
            //新增
            shop.setId(idWorker.nextId() + "");
            shop.setTime(new Date());
            shop.setState("1");
            shop.setPrice((int)(price1 * 100));
            shopMapper.insert(shop);
            return "redirect:/shop";
        } else {
            shop.setPrice((int)(price1 * 100));
            shopMapper.updateByPrimaryKeySelective(shop);
            return "redirect:/shop";
        }
    }

    /**
     * 套餐
     *
     * @param shop
     * @param request
     * @return
     */
    @RequestMapping(value = "double/save", method = RequestMethod.POST)
    public String doubleSave(Shop shop, Double price1,HttpServletRequest request) {
        String[] oneIds = request.getParameterValues("oneId");
        String[] nums = request.getParameterValues("num");
        if (shop.getId() == null) {
            //新增
            shop.setId(idWorker.nextId() + "");
            shop.setTime(new Date());
            shop.setState("2");
            shop.setPrice((int)(price1 * 100));
            shopMapper.insert(shop);
            /**
             * 保存套餐单品
             */
            for (int i = 0; i < oneIds.length; i++) {
                DoubleShop doubleShop = new DoubleShop();
                for (int i1 = 0; i1 < nums.length; i1++) {
                    doubleShop.setId(idWorker.nextId() + "");
                    doubleShop.setShopId(shop.getId());
                    doubleShop.setNum(Integer.valueOf(nums[i]));
                    doubleShop.setOneId(oneIds[i]);
                }
                doubleShopMapper.insert(doubleShop);
            }
            return "redirect:/shop";
        } else {
            shop.setPrice((int)(price1 * 100));
            shopMapper.updateByPrimaryKeySelective(shop);
            /**
             * 删除单品重新保存
             */
            Example example = new Example(DoubleShop.class);
            example.createCriteria().andEqualTo("shopId",shop.getId());
            doubleShopMapper.deleteByExample(example);
            /**
             * 保存套餐单品
             */
            for (int i = 0; i < oneIds.length; i++) {
                DoubleShop doubleShop = new DoubleShop();
                for (int i1 = 0; i1 < nums.length; i1++) {
                    doubleShop.setId(idWorker.nextId() + "");
                    doubleShop.setShopId(shop.getId());
                    doubleShop.setNum(Integer.valueOf(nums[i]));
                    doubleShop.setOneId(oneIds[i]);
                }
                doubleShopMapper.insert(doubleShop);
            }
            return "redirect:/shop";
        }
    }

    @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Shop shop = shopMapper.selectByPrimaryKey(id);
        map.put("shop", shop);
        if ("2".equals(shop.getState())) {
            /**
             * 套餐
             */
            Example example = new Example(Shop.class);
            example.createCriteria().andEqualTo("state", "1");
            List<Shop> shops = shopMapper.selectByExample(example);
            map.put("shop1", shops);
            map.put("shop", shop);
            Example example1 = new Example(DoubleShop.class);
            example1.createCriteria().andEqualTo("shopId", shop.getId());
            List<DoubleShop> doubleShops = doubleShopMapper.selectByExample(example1);
            map.put("doubleShop", doubleShops);
            return "system/shop/shop_double_update";
        } else {
            return "system/shop/shop_update";
        }
    }

}
