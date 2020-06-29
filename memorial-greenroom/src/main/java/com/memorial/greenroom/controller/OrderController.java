package com.memorial.greenroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.constants.StatusCode;
import com.memorial.common.dto.PageResultDTO;
import com.memorial.common.dto.ResultDTO;
import com.memorial.mapper.*;
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
 * @date 2020/5/2 11:48
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private DetailsMapper detailsMapper;
    @Autowired
    private MemorialMapper memorialMapper;

    @RequestMapping(method = RequestMethod.GET)
    public String order() {
        return "system/order/order_list";
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
    public PageResultDTO<Order> list(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Order.class);
        example.createCriteria().andNotEqualTo("state", "1");
        example.setOrderByClause(" time desc ");
        List<Order> orders = orderMapper.selectByExample(example);
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                User user = userMapper.selectByPrimaryKey(order.getUserId());
                order.setUserName(user.getName());
                Details details = detailsMapper.selectByPrimaryKey(order.getDetailsId());
                Memorial memorial = memorialMapper.selectByPrimaryKey(order.getDetailsId());
                if (StringUtils.isEmpty(details) && StringUtils.isEmpty(memorial)) {
                    order.setUserImg("已下架");
                    order.setShopImg("已下架");
                } else {
                    if (!StringUtils.isEmpty(details)) {
                        order.setUserImg(details.getTitle());
                        order.setShopImg(details.getId());
                    }else {
                        order.setUserImg(memorial.getName());
                        order.setShopImg(memorial.getId());
                    }
                }
                Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
                order.setShopName(shop.getName());
                order.setText(shop.getState());
            }
        }
        PageInfo<Order> sysBannerPageInfo = new PageInfo<>(orders);
        return new PageResultDTO<>(sysBannerPageInfo.getTotal(), orders);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResultDTO delete(@PathVariable("id") String id) {
        orderMapper.deleteByPrimaryKey(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查看详情
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, Map<String, Object> map) {
        Order order = orderMapper.selectByPrimaryKey(id);
        map.put("order", order);
        return "system/order/order_view";
    }

    /*@RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Banner banner) {
        if (banner.getId() == null) {
            //新增
            banner.setId(idWorker.nextId() + "");
            banner.setTime(new Date());
            orderMapper.insert(banner);
            return "redirect:/banner";
        } else {
            orderMapper.updateByPrimaryKeySelective(banner);
            return "redirect:/banner";
        }
    }*/

   /* @RequestMapping("hx")
    public String hx(Map<String, Object> map, String id) {
        Banner banner = orderMapper.selectByPrimaryKey(id);
        map.put("banner",banner);
        return "system/order/order_update";
    }*/
}
