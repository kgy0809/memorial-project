package com.memorial.item.service;

import com.memorial.common.enums.ExceptionEnum;
import com.memorial.common.exception.LyException;
import com.memorial.common.utils.IdWorker;
import com.memorial.item.config.OpenIdProperties;
import com.memorial.item.utils.sdk.WXPayOrder;
import com.memorial.mapper.OrderMapper;
import com.memorial.mapper.ShopMapper;
import com.memorial.mapper.UserMapper;
import com.memorial.pojo.Order;
import com.memorial.pojo.Shop;
import com.memorial.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/5 14:38
 */
@Service
public class OrderService {

    @Autowired
    private OpenIdProperties openIdProperties;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 根据订单id查询订单
     *
     * @param out_trade_no
     * @return
     */
    public Order queryByOrderId(String out_trade_no) {
        return orderMapper.selectByPrimaryKey(out_trade_no);
    }

    /**
     * 修改订单状态
     *
     * @param out_trade_no
     */
    public void updateOrderState(String out_trade_no) {
        Order order = new Order();
        order.setId(out_trade_no);
        order.setState("2");
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 创建订单 返回支付信息
     *
     * @param id     用户ID
     * @param shopId 商品ID
     * @return
     */
    @Transactional
    public Map<String, Object> createOrder(String id, String shopId, String text, String detailsId) {
        Order order = new Order();
        order.setId(idWorker.nextId() + "");
        order.setShopId(shopId);
        order.setText(text);
        order.setUserId(id);
        order.setTime(new Date());
        order.setState("1");
        order.setDetailsId(detailsId);
        User user = userMapper.selectByPrimaryKey(id);
        order.setOpenId(user.getOpenId());
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        order.setPrice(shop.getPrice());
        orderMapper.insert(order);
        Map<String, Object> map = new HashMap<>(2);
        if (0 == shop.getPrice()){
            /**
             * 0元支付
             */
            order.setState("2");
            orderMapper.updateByPrimaryKeySelective(order);
            map.put("state", "2");
            return map;
        }
        /**
         * 微信支付
         */
        WXPayOrder wxPayOrder = new WXPayOrder(order,openIdProperties.getNOTIFY_URL());
        Map<String, Object> str = null;
        try {
            str = wxPayOrder.getParameter();
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_ORDER_STATUS);
        }
        map.put("str", str);
        map.put("state", "1");
        return map;
    }
}
