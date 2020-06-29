package com.memorial.item.job;

import com.memorial.mapper.OrderMapper;
import com.memorial.mapper.ShopMapper;
import com.memorial.pojo.Order;
import com.memorial.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/4/30 18:30
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class SaticScheduleTask {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 每分钟查询数据库对订单的时间进行统计
     */
    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void first() {
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo("state", "2");
        List<Order> orders = orderMapper.selectByExample(example);
        if (!orders.isEmpty()) {
            /**
             * 计算判断订单的时间跟当前时间 差值 小于小时 修改订单的状态 3
             */
            long nh = 1000 * 60 * 60;
            long nd = 1000 * 24 * 60 * 60;

            for (Order order : orders) {
                Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
                Date date = new Date();
                long diff = date.getTime() - order.getTime().getTime();
                // 计算差多少天
                long day = diff / nd;
                // 计算差多少小时
                long hour = diff % nd / nh + day * 24;
                if (hour > shop.getHour()) {
                    order.setState("3");
                    orderMapper.updateByPrimaryKeySelective(order);
                }
            }
        }

    }
}
