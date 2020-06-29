package com.memorial.item.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.memorial.common.utils.IdWorker;
import com.memorial.item.dto.*;
import com.memorial.mapper.*;
import com.memorial.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author kgy
 * @version 1.0
 * @date 2020/5/15 17:08
 */
@Service
public class MemorialService {

    @Autowired
    private MemorialMapper memorialMapper;
    @Autowired
    private CpmMapper cpmMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PlazaMsgImgMapper plazaMsgImgMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private DoubleShopMapper doubleShopMapper;
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 创建纪念馆
     *
     * @param name 名字
     * @param site 地点
     * @param text 简介
     */
    public String creationMemorial(String name, String site, String text, String userId, String dateOfBirth1, String trendDate1, String dateOfBirth2, String trendDate2) {
        Memorial memorial = new Memorial();
        memorial.setId(idWorker.nextId() + "");
        memorial.setName(name);
        memorial.setSite(site);
        memorial.setText(text);
        memorial.setUserId(userId);
        memorial.setState("2");
        memorial.setTime(new Date());
        memorial.setDate1(dateOfBirth1);
        memorial.setTrend1(trendDate1);
        memorial.setDate2(dateOfBirth2);
        memorial.setTrend2(trendDate2);
        memorialMapper.insert(memorial);
        return memorial.getId();
    }

    /**
     * 弹窗信息
     *
     * @return
     */
    public Cpm cpmMemorial() {
        return cpmMapper.selectAll().get(0);
    }

    /**
     * 祠堂 查询
     *
     * @param page
     * @param rows
     * @param type   1所有 2 自己的
     * @param name   名字
     * @param site   地址
     * @param userId
     */
    public PageInfo<MemorialDto> queryList(Integer page, Integer rows, String type, String name, String site, String userId) {
        List<MemorialDto> memorialDtos = null;
        List<PlazaMsgImg> plazaMsgImgs = plazaMsgImgMapper.selectAll();
        if ("1".equals(type)) {
            if (StringUtils.isEmpty(name) && StringUtils.isEmpty(site)) {
                /**
                 * 搜 所有
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());

                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(site)) {
                /**
                 * name 跟 site  一起进行查询
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("name", "%" + name + "%").andLike("site", "%" + site + "%");
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(name)) {
                /**
                 * 根据name搜
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("name", "%" + name + "%");
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(site)) {
                /**
                 * 根据 site搜
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("site", "%" + site + "%");
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            }
        } else if ("2".equals(type)) {
            /**
             * 搜自己
             */
            /*PageHelper.startPage(page, rows);
            Example example = new Example(Memorial.class);
            example.setOrderByClause(" time desc ");
            example.createCriteria().andEqualTo("userId", userId);
            List<Memorial> memorials = memorialMapper.selectByExample(example);
            memorialDtos = new ArrayList<>();
            if (!memorials.isEmpty()) {
                for (Memorial memorial : memorials) {
                    MemorialDto memorialDto = new MemorialDto();
                    List<String> list = new ArrayList<>();
                    String[] split = memorial.getName().split("-");
                    if (split.length > 0) {
                        for (String s : split) {
                            String[] split1 = s.split(",");
                            list.add(split1[0]);
                        }
                    }
                    Example orderEx = new Example(Order.class);
                    orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                    List<Order> orders = orderMapper.selectByExample(orderEx);
                    memorialDto.setId(memorial.getId());
                    memorialDto.setName(list);
                    memorialDto.setSite(memorial.getSite());
                    memorialDto.setText(memorial.getText());
                    memorialDto.setShopSize(orders.size());
                    memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                    memorialDtos.add(memorialDto);
                }
            }
            return new PageInfo<>(memorialDtos);*/

            if (StringUtils.isEmpty(name) && StringUtils.isEmpty(site)) {
                /**
                 * 搜 所有
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andEqualTo("userId", userId);
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());

                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(site)) {
                /**
                 * name 跟 site  一起进行查询
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("name", "%" + name + "%").andLike("site", "%" + site + "%").andEqualTo("userId", userId);
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(name)) {
                /**
                 * 根据name搜
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("name", "%" + name + "%").andEqualTo("userId", userId);
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            } else if (!StringUtils.isEmpty(site)) {
                /**
                 * 根据 site搜
                 */
                PageHelper.startPage(page, rows);
                Example example = new Example(Memorial.class);
                example.createCriteria().andLike("site", "%" +site+ "%").andEqualTo("userId", userId);
                example.setOrderByClause(" time desc ");
                List<Memorial> memorials = memorialMapper.selectByExample(example);
                memorialDtos = new ArrayList<>();
                if (!memorials.isEmpty()) {
                    for (Memorial memorial : memorials) {
                        MemorialDto memorialDto = new MemorialDto();
                        List<String> list = new ArrayList<>();
                        String[] split = memorial.getName().split("-");
                        if (split.length > 0) {
                            for (String s : split) {
                                String[] split1 = s.split(",");
                                list.add(split1[0]);
                            }
                        }
                        Example orderEx = new Example(Order.class);
                        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                        List<Order> orders = orderMapper.selectByExample(orderEx);
                        memorialDto.setId(memorial.getId());
                        memorialDto.setName(list);
                        memorialDto.setSite(memorial.getSite());
                        memorialDto.setText(memorial.getText());
                        memorialDto.setShopSize(orders.size());
                        memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                        memorialDtos.add(memorialDto);
                    }
                }
                return new PageInfo<>(memorialDtos);
            }

        } else if ("3".equals(type)) {
            /**
             * 搜首页
             */
            PageHelper.startPage(page, rows);
            Example example = new Example(Memorial.class);
            example.setOrderByClause(" time desc ");
            example.createCriteria().andEqualTo("state", "1");
            List<Memorial> memorials = memorialMapper.selectByExample(example);
            memorialDtos = new ArrayList<>();
            if (!memorials.isEmpty()) {
                for (Memorial memorial : memorials) {
                    MemorialDto memorialDto = new MemorialDto();
                    List<String> list = new ArrayList<>();
                    String[] split = memorial.getName().split("-");
                    if (split.length > 0) {
                        for (String s : split) {
                            String[] split1 = s.split(",");
                            list.add(split1[0]);
                        }
                    }
                    Example orderEx = new Example(Order.class);
                    orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
                    List<Order> orders = orderMapper.selectByExample(orderEx);
                    memorialDto.setId(memorial.getId());
                    memorialDto.setName(list);
                    memorialDto.setSite(memorial.getSite());
                    memorialDto.setText(memorial.getText());
                    memorialDto.setShopSize(orders.size());
                    memorialDto.setImg(plazaMsgImgs.get(0).getImg());
                    memorialDtos.add(memorialDto);
                }
            }
            return new PageInfo<>(memorialDtos);
        }
        return new PageInfo<>();
    }

    /**
     * 任务详情
     *
     * @param id
     * @return
     */
    public MemorialDto2 queryById(String id) {
        Memorial memorial = memorialMapper.selectByPrimaryKey(id);
        List<PlazaMsgImg> plazaMsgImgs = plazaMsgImgMapper.selectAll();
        Example orderEx = new Example(Order.class);
        orderEx.createCriteria().andEqualTo("detailsId", memorial.getId()).andNotEqualTo("state", "1");
        List<Order> orders = orderMapper.selectByExample(orderEx);
        MemorialDto2 memorialDto2 = new MemorialDto2();
        List<String> list = new ArrayList<>();
        String[] split = memorial.getName().split("-");
        if (split.length > 0) {
            for (String s : split) {
                String[] split1 = s.split(",");
                list.add(split1[0]);
            }
        }
        memorialDto2.setId(memorial.getId());
        memorialDto2.setName(list);
        memorialDto2.setSite(memorial.getSite());
        memorialDto2.setText(memorial.getText());
        memorialDto2.setShopSize(orders.size());
        memorialDto2.setImg(plazaMsgImgs.get(0).getImg());
        if (StringUtils.isEmpty(memorial.getDate1())) {
            memorialDto2.setDate1("");
        } else {
            memorialDto2.setDate1(memorial.getDate1());
        }
        if (StringUtils.isEmpty(memorial.getDate2())) {
            memorialDto2.setDate2("");
        } else {
            memorialDto2.setDate2(memorial.getDate2());
        }
        if (StringUtils.isEmpty(memorial.getTrend1())) {
            memorialDto2.setTrend1("");
        } else {
            memorialDto2.setTrend1(memorial.getTrend1());
        }
        if (StringUtils.isEmpty(memorial.getTrend2())) {
            memorialDto2.setTrend2("");
        } else {
            memorialDto2.setTrend2(memorial.getTrend2());
        }
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
                example2.createCriteria().andEqualTo("shopId", shop.getId()).andNotEqualTo("state", "1").andEqualTo("detailsId", memorial.getId());
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
                example2.createCriteria().andEqualTo("shopId", shop.getId()).andNotEqualTo("state", "1").andEqualTo("detailsId", memorial.getId());
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
        memorialDto2.setShopDtos(shopDtos);
        return memorialDto2;
    }

    /**
     * 修改回显
     *
     * @param id
     * @return
     */
    public NameAndRelationDto updateById(String id) {
        Memorial memorial = memorialMapper.selectByPrimaryKey(id);
        NameAndRelationDto nameAndRelationDto = new NameAndRelationDto();
        List<NameAndRelationDto2> list = new ArrayList<>();
        nameAndRelationDto.setId(memorial.getId());
        nameAndRelationDto.setSite(memorial.getSite());
        nameAndRelationDto.setText(memorial.getText());
        String[] split = memorial.getName().split("-");
        if (split.length > 0) {
            for (String s : split) {
                NameAndRelationDto2 nameAndRelationDto2 = new NameAndRelationDto2();
                String[] split1 = s.split(",");
                nameAndRelationDto2.setName(split1[0]);
                nameAndRelationDto2.setRelation(split1[1]);
                list.add(nameAndRelationDto2);
            }
        }
        nameAndRelationDto.setList(list);
        if (StringUtils.isEmpty(memorial.getDate1())) {
            nameAndRelationDto.setDate1("");
        } else {
            nameAndRelationDto.setDate1(memorial.getDate1());
        }
        if (StringUtils.isEmpty(memorial.getDate2())) {
            nameAndRelationDto.setDate2("");
        } else {
            nameAndRelationDto.setDate2(memorial.getDate2());
        }
        if (StringUtils.isEmpty(memorial.getTrend1())) {
            nameAndRelationDto.setTrend1("");
        } else {
            nameAndRelationDto.setTrend1(memorial.getTrend1());
        }
        if (StringUtils.isEmpty(memorial.getTrend2())) {
            nameAndRelationDto.setTrend2("");
        } else {
            nameAndRelationDto.setTrend2(memorial.getTrend2());
        }
        return nameAndRelationDto;
    }

    public void updateMemorial(String name, String site, String text, String id, String dateOfBirth1, String trendDate1, String dateOfBirth2, String trendDate2) {
        Memorial memorial = new Memorial();
        memorial.setId(id);
        memorial.setName(name);
        memorial.setSite(site);
        memorial.setText(text);
        memorial.setDate1(dateOfBirth1);
        memorial.setTrend1(trendDate1);
        memorial.setDate2(dateOfBirth2);
        memorial.setTrend2(trendDate2);
        memorialMapper.updateByPrimaryKeySelective(memorial);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        memorialMapper.deleteByPrimaryKey(id);
    }

    /**
     * 模板查询
     *
     * @return
     */
    public List<Template> getTemplate() {
        return templateMapper.selectAll();
    }

    /**
     * 查看祠堂
     *
     * @param id
     */
    public GiftAllDto lookClanById(String id) {
        Memorial memorial = memorialMapper.selectByPrimaryKey(id);
        String[] split = memorial.getName().split("-");
        List<String> names = new ArrayList<>();
        for (String s : split) {
            String[] split1 = s.split(",");
            names.add(split1[0]);
        }
        GiftAllDto giftAllDto = new GiftAllDto();
        giftAllDto.setName(names);
        Example example1 = new Example(Shop.class);
        example1.createCriteria().andEqualTo("state", "1");
        List<Shop> shops = shopMapper.selectByExample(example1);
        /**
         * 计算判断订单的时间跟当前时间 差值 小于小时
         */
        long nh = 1000 * 60 * 60;
        long nd = 1000 * 24 * 60 * 60;
        Date date = new Date();
        /**
         * 单品统计
         */
        if (!shops.isEmpty()) {
            for (int i = 0; i < shops.size(); i++) {
                Example example2 = new Example(Order.class);
                example2.createCriteria().andEqualTo("shopId", shops.get(i).getId()).andEqualTo("state", "2").andEqualTo("detailsId", id);
                example2.setOrderByClause(" time desc ");
                List<Order> orders = orderMapper.selectByExample(example2);
                List<GiftDto> list = new ArrayList<>();
                if (!orders.isEmpty()) {
                    for (Order order : orders) {
                        Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
                        User user = userMapper.selectByPrimaryKey(order.getUserId());
                        GiftDto giftDto = new GiftDto();
                        giftDto.setName(user.getName());
                        giftDto.setUrl(shop.getImg());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(order.getTime());
                        cal.add(Calendar.HOUR, shop.getHour());// 24小时制
                        DateTime dateTime = DateUtil.date(date);
                        giftDto.setTime(DateUtil.betweenMs(cal.getTime(), dateTime) + "");
                        list.add(giftDto);
                    }
                }
                switch (i) {
                    case 0:
                        giftAllDto.setList1(list);
                        break;
                    case 1:
                        giftAllDto.setList2(list);
                        break;
                    case 2:
                        giftAllDto.setList3(list);
                        break;
                    case 3:
                        giftAllDto.setList4(list);
                        break;
                    case 4:
                        giftAllDto.setList5(list);
                        break;
                    case 5:
                        giftAllDto.setList6(list);
                        break;
                    case 6:
                        giftAllDto.setList7(list);
                        break;
                    case 7:
                        giftAllDto.setList8(list);
                        break;
                    case 8:
                        giftAllDto.setList9(list);
                        break;
                    case 9:
                        giftAllDto.setList10(list);
                        break;
                    case 10:
                        giftAllDto.setList11(list);
                        break;
                    case 11:
                        giftAllDto.setList12(list);
                        break;
                    case 12:
                        giftAllDto.setList13(list);
                        break;
                    case 13:
                        giftAllDto.setList14(list);
                        break;
                    default:
                        break;
                }
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

            for (int i = 0; i < shops3.size(); i++) {
                Example example2 = new Example(Order.class);
                example2.createCriteria().andEqualTo("shopId", shops3.get(i).getId()).andEqualTo("state", "2").andEqualTo("detailsId", memorial.getId());
                example2.setOrderByClause(" time desc ");
                List<Order> orders1 = orderMapper.selectByExample(example2);
                List<GiftDto> list = new ArrayList<>();
                if (!orders1.isEmpty()) {
                    for (Order order : orders1) {
                        Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
                        User user = userMapper.selectByPrimaryKey(order.getUserId());
                        GiftDto giftDto = new GiftDto();
                        giftDto.setName(user.getName());
                        giftDto.setUrl(shop.getImg());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(order.getTime());
                        cal.add(Calendar.HOUR, shop.getHour());// 24小时制
                        DateTime dateTime = DateUtil.date(date);
                        giftDto.setTime(DateUtil.betweenMs(cal.getTime(), dateTime) + "");
                        list.add(giftDto);
                    }
                }
                switch (i) {
                    case 0:
                        giftAllDto.setList15(list);
                        break;
                    case 1:
                        giftAllDto.setList16(list);
                        break;
                    case 2:
                        giftAllDto.setList17(list);
                        break;
                    default:
                        break;
                }
            }
        }
        return giftAllDto;
    }

    /**
     * 查看 记录册
     *
     * @param id
     * @param page
     * @param rows
     */
    public PageInfo<Order> lookBookById(String id, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo("detailsId", id).andNotEqualTo("state", "1");
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
