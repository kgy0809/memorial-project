package com.memorial.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.item.dto.DoubleShopDto;
import com.memorial.item.dto.OrderDto;
import com.memorial.item.dto.ShopDto;
import com.memorial.mapper.*;
import com.memorial.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/29 9:48
 */
@Service
public class DetailsService {

    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private DoubleShopMapper doubleShopMapper;

    /**
     * 首页追忆展示
     *
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Details> queryDetailsList(Integer page, Integer rows) {
        Example example1 = new Example(Details.class);
        example1.createCriteria().andEqualTo("state", "1");
        example1.setOrderByClause(" time desc ");
        List<Details> details1 = detailsMapper.selectByExample(example1);
        if (!details1.isEmpty()) {
            for (Details details : details1) {
                details.setTime2(new Date());
                detailsMapper.updateByPrimaryKeySelective(details);
            }
        }

        PageHelper.startPage(page, rows);
        Example example = new Example(Details.class);
        example.setOrderByClause(" time2 desc");
        List<Details> details = detailsMapper.selectByExample(example);
        return new PageInfo<>(details);
    }

    /**
     * 追忆详情
     *
     * @param id
     * @return
     */
    public OrderDto queryById(String id) {
        Details details = detailsMapper.selectByPrimaryKey(id);
        /**
         * 纪念薄数量查询
         */
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo("detailsId", id).andNotEqualTo("state","1");
        List<Order> orders = orderMapper.selectByExample(example);
        /**
         * 统计参与人数以及礼物个数
         */
        Example example1 = new Example(Shop.class);
        example1.createCriteria().andEqualTo("state", "1");
        List<Shop> shops = shopMapper.selectByExample(example1);
        /**
         * 单品统计
         */
        List<ShopDto> shopDtos = new ArrayList<>();
        if (!shops.isEmpty()) {
            for (Shop shop : shops) {
                ShopDto shopDto = new ShopDto();
                Example example2 = new Example(Order.class);
                example2.createCriteria().andEqualTo("shopId", shop.getId()).andNotEqualTo("state", "1").andEqualTo("detailsId", id);
                List<Order> orders1 = orderMapper.selectByExample(example2);
                shopDto.setId(shop.getId());
                shopDto.setName(shop.getName());
                shopDto.setNum(orders1.size());
                shopDtos.add(shopDto);
            }
        }
        /**
         * 套餐统计
         */
        Example example3 = new Example(Shop.class);
        example3.createCriteria().andEqualTo("state", "2");
        List<Shop> shops3 = shopMapper.selectByExample(example3);
        /**
         * 统计查询套餐订单
         */
        if (!shops3.isEmpty()) {
            for (Shop shop : shops3) {
                Example example2 = new Example(Order.class);
                example2.createCriteria().andEqualTo("shopId", shop.getId()).andNotEqualTo("state", "1").andEqualTo("detailsId", id);
                List<Order> orders1 = orderMapper.selectByExample(example2);
                if (!orders1.isEmpty()) {
                    for (Order order : orders1) {
                        Example example4 = new Example(DoubleShop.class);
                        example4.createCriteria().andEqualTo("shopId", order.getShopId());
                        List<DoubleShop> doubleShops = doubleShopMapper.selectByExample(example4);
                        if (!doubleShops.isEmpty()) {
                            for (DoubleShop doubleShop : doubleShops) {
                                for (ShopDto shopDto : shopDtos) {
                                    if (doubleShop.getOneId().equals(shopDto.getId())) {
                                        shopDto.setNum(shopDto.getNum() + doubleShop.getNum());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setDetails(details);
        orderDto.setShopDtos(shopDtos);
        orderDto.setGiftSize(orders.size());
        return orderDto;
    }

    /**
     * @param type 2套餐 1单品
     * @return
     */
    public List<Shop> queryShop(String type) {
        Example example = new Example(Shop.class);
        example.createCriteria().andEqualTo("state", type);
        return shopMapper.selectByExample(example);
    }

    /**
     * 套餐详情
     * @param id
     * @return
     */
    public DoubleShopDto queryShopById(String id) {
        DoubleShopDto doubleShopDto = new DoubleShopDto();
        Shop shop = shopMapper.selectByPrimaryKey(id);
        Example example = new Example(DoubleShop.class);
        example.createCriteria().andEqualTo("shopId",shop.getId());
        List<DoubleShop> doubleShops = doubleShopMapper.selectByExample(example);
        List<ShopDto> shopDtos = new ArrayList<>();
        if (!doubleShops.isEmpty()){
            for (DoubleShop doubleShop : doubleShops) {
                ShopDto shopDto = new ShopDto();
                Shop shop1 = shopMapper.selectByPrimaryKey(doubleShop.getOneId());
                shopDto.setName(shop1.getName());
                shopDto.setId(shop1.getId());
                shopDto.setNum(doubleShop.getNum());
                shopDtos.add(shopDto);
            }
        }
        doubleShopDto.setShop(shop);
        doubleShopDto.setShopDtoList(shopDtos);
        return doubleShopDto;
    }

    public PageInfo<Order> queryByIdDetails(Integer page, Integer rows,String id) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo("detailsId", id).andNotEqualTo("state","1");
        example.setOrderByClause(" time desc ");
        List<Order> orders = orderMapper.selectByExample(example);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                User user = userMapper.selectByPrimaryKey(order.getUserId());
                order.setUserImg(user.getImg());
                order.setUserName(user.getName());
                Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
                order.setShopName(shop.getName());
                order.setShopImg(shop.getImg());
            }
        }
        return new PageInfo<>(orders);
    }
}
