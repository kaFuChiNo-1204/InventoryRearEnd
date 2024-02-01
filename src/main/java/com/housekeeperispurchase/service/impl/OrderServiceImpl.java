package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.AdditiveMapper;
import com.housekeeperispurchase.mapper.GoodsMapper;
import com.housekeeperispurchase.mapper.ProfitMapper;
import com.housekeeperispurchase.pojo.*;
import com.housekeeperispurchase.mapper.OrderMapper;
import com.housekeeperispurchase.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodstypeoneServiceImpl goodstypeoneService;

    @Autowired
    private AdditiveServiceImpl additiveService;

    @Autowired
    private ProfitServiceImpl profitService;

    @Autowired
    private ProfitMapper profitMapper;

    @Autowired
    private FinanceServiceImpl financeService;


    //    @ApiOperation("获取所有的订单摘要")
    public ResponseResult getOrderNameCode() {
        QueryWrapper queryWrapper = new QueryWrapper<Order>();
        queryWrapper.select("orderName", "orderCode");
        List list = orderMapper.selectList(queryWrapper);
        List<String> list1 = new ArrayList<>();
        for (Object o : list) {
            Order order = (Order) o;
            list1.add(order.getOrderName() + order.getOrderCode());
        }
        Set<String> set = new HashSet<>(list1);
        List<String> deduplicatedList = new ArrayList<>(set);
        return new ResponseResult(200, "查询成功", deduplicatedList);
    }

    //    @ApiOperation("获取所有入库人员")
    public ResponseResult getOrderPushUser() {
        List<Order> orderPushUserId = orderMapper.selectList(new QueryWrapper<Order>().select("orderPushUserId"));
        List list = new ArrayList();
        for (Order order : orderPushUserId) {
            String userName = userService.getUserNameById(order.getOrderPushUserId().toString());
            Map<String, String> map = new HashMap();
            if (EmptyChecker.notEmpty(order.getOrderPushUserId().toString())) {
                map.put("userId", order.getOrderPushUserId().toString());
                map.put("userName", userName);
                list.add(map);
            }
        }
        Set<String> set = new HashSet<>(list);
        List<String> deduplicatedList = new ArrayList<>(set);
        return new ResponseResult(200, "查询成功", deduplicatedList);
    }

    //    @ApiOperation("获取所有出库人员")
    public ResponseResult getOrderSalesCode() {
        List<Order> orderPushUserId = orderMapper.selectList(new QueryWrapper<Order>().select("orderSalesUserId"));
        List list = new ArrayList();
        for (Order order : orderPushUserId) {
            String userName = userService.getUserNameById(order.getOrderSalesUserId().toString());
            Map<String, String> map = new HashMap();
            if (EmptyChecker.notEmpty(order.getOrderSalesUserId().toString())) {
                map.put("userId", order.getOrderSalesUserId().toString());
                map.put("userName", userName);
                list.add(map);
            }
        }
        Set<String> set = new HashSet<>(list);
        List<String> deduplicatedList = new ArrayList<>(set);
        return new ResponseResult(200, "查询成功", deduplicatedList);
    }

    //    @ApiOperation("新增订单")
    public ResponseResult addOrder(Order order) {
        Goods goods = new Goods();
        // 根据库存id获取数据
        try {
            System.out.println("order_____"+order);
            goods = goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goodsId", order.getGoodsId()));
            System.out.println("goods---------"+goods);
            if (EmptyChecker.notEmpty(goods.getGoodsId())) {
                String code = goods.getGoodsCode().replaceAll(" ","");
                System.out.println(code);
                // 获取编码
                order.setOrderCode(code);
                // 获取名字
                order.setOrderName(goods.getGoodsName());
                // 获取大类别
                order.setOrderTypeOneId(goods.getGoodsTypeOneId());
                // 获取入库人员
                order.setOrderPushUserId(goods.getGoodsPushUserId());
                // 获取回收人员,即出库人员
                order.setOrderRecoupUserId(order.getOrderSalesUserId());
                // 获取附加成本,先默认给0
                order.setOrderAdditivePrice(BigDecimal.valueOf(0));
                int priceTotal = 0;
                if (EmptyChecker.notEmpty(goods.getAdditiveId())) {
                    List<Additive> additives = additiveService.AdditiveById(goods.getAdditiveId());
                    for (Additive additive : additives) {
                        priceTotal += additive.getAdditivePrice();
                    }
                    // 获取附加成本
                    order.setOrderAdditivePrice(BigDecimal.valueOf(priceTotal));
                }
                // 获取总成本
                order.setOrderCostPrice(BigDecimal.valueOf(priceTotal).add(goods.getGoodsPushPrice()));

                // 计算利润
                order.setOrderProfitPrice(order.getOrderSellingPrice().subtract(order.getOrderCostPrice()));
            }
        } catch (Exception e) {
            return new ResponseResult(202, "参数转换失败");
        }
//        System.out.println("order____" + order);
        int insert = orderMapper.insert(order);
        if (insert > 0) {
            // 将该库存设置为出库，不再显示
            goods.setGoodsDisabled("出库");
            goodsMapper.updateById(goods);
            // 如果订单是已结，就添加给员工添加利润
            if (order.getOrderStaticId() == 1) {
                //  加利润
                Boolean aBoolean = addProfit(order, goods);
                //  加财务
                Finance finance = new Finance();
                finance.setFinanceType("收入");
                finance.setIncomeId(1);
                BigDecimal priceTotal = goods.getGoodsPushPrice();
                if (EmptyChecker.notEmpty(goods.getAdditiveId())) {
                    List<Additive> list = additiveService.AdditiveById(goods.getAdditiveId());
                    for (Additive additive : list) {
                        priceTotal = priceTotal.add(new BigDecimal(additive.getAdditivePrice()));
                    }
                }
                finance.setFinanceBalance("+" + goods.getGoodsPushPrice());
                //  退单时间应该是当前时间
                LocalDateTime currentDateTime = LocalDateTime.now();
                // 定义要使用的格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 格式化当前时间为字符串
                String formattedDateTimeString = currentDateTime.format(formatter);
                // 将格式化后的字符串转换回 LocalDateTime
                LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTimeString, formatter);
                finance.setFinanceTime(parsedDateTime);
                finance.setFinanceName(goods.getGoodsName() + goods.getGoodsCode());
                finance.setFinanceUserId(goods.getGoodsPushUserId());
                Boolean aBoolean1 = financeService.addFinance(finance);
                System.out.println("是否添加财务成功"+aBoolean1);
                if (aBoolean && aBoolean1) return new ResponseResult(200, "添加成功");
                return new ResponseResult(202, "添加失败");
            }
            return new ResponseResult(200, "添加成功");
        }
        return new ResponseResult(202, "添加失败");
    }

    //  添加利润的方法
    public Boolean addProfit(Order order, Goods goods) {
        // 总利润 = 卖价 -（商品成本+附加成本）
        BigDecimal add = order.getOrderProfitPrice();
        // 1.先判断销售人员和入库人员是不是同一个
        // 1.1是，添加一条工资明细
        if (order.getOrderSalesUserId().equals(order.getOrderPushUserId())) {
            Profit profit = new Profit();
            profit.setProfitUserId(order.getOrderSalesUserId());
            profit.setProfitName(order.getOrderName() + order.getOrderCode());
            // 回收和销售是一个人  总利润*20%
            BigDecimal divide = add.multiply(new BigDecimal("0.20"));
            profit.setProfitPrice(divide);
            profit.setProfitType("销售回收");
            // 个人业绩,是一个人  总利润*100%
            profit.setProfitOutStanding(add.toString());
            // 销售时间
            profit.setProfitTime(order.getOrderPushTime());
            Boolean aBoolean = profitService.addProfit(profit);
            if (aBoolean) return true;
            return false;
        }
        // 1.2不是，添加两条
        Profit profit1 = new Profit();// 回收人员
        Profit profit2 = new Profit();// 销售人员
        profit1.setProfitUserId(order.getOrderPushUserId());
        profit1.setProfitName(order.getOrderName() + order.getOrderCode());

        // 回收和销售是两个人  总利润*20%/2
        BigDecimal divide1 = add.multiply(new BigDecimal("0.20")).divide(new BigDecimal("2"));
        profit1.setProfitPrice(divide1);
        profit1.setProfitType("回收");
        // 个人业绩,是两个人  总利润*50%
        profit1.setProfitOutStanding(add.multiply(new BigDecimal("0.50")).toString());
        // 销售时间
        profit1.setProfitTime(goods.getGoodsTime());

        profit2.setProfitUserId(order.getOrderSalesUserId());
        profit2.setProfitName(order.getOrderName() + order.getOrderCode());
        // 回收和销售是两个人  总利润*20%/2
        BigDecimal divide2 = add.multiply(new BigDecimal("0.20")).divide(new BigDecimal("2"));
        profit2.setProfitPrice(divide2);
        profit2.setProfitType("销售");
        // 个人业绩,是两个人  总利润*50%
        profit2.setProfitOutStanding(add.multiply(new BigDecimal("0.50")).toString());
        // 销售时间
        profit2.setProfitTime(goods.getGoodsTime());

        Boolean aBoolean1 = profitService.addProfit(profit1);
        Boolean aBoolean2 = profitService.addProfit(profit2);
        return aBoolean1 && aBoolean2;
    }

    //    @ApiOperation("获取订单内容")
    public ResponseResult getOrderData(String NameCode, String pushId, String saleId, String orderStaticId, String[] time, Long current, Long limit) {
        // Step1：创建一个 QueryWrapper 对象
        QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
        // Step2： 构造查询条件
        if (EmptyChecker.isEmpty(time)) {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(pushId), "orderPushUserId", pushId)
                    .eq(EmptyChecker.notEmpty(saleId), "orderSalesUserId", saleId)
                    .eq(EmptyChecker.notEmpty(orderStaticId), "orderStaticId", orderStaticId)
                    .ge(EmptyChecker.isEmpty(time), "orderPushTime", time[0])
                    .le(EmptyChecker.isEmpty(time), "orderPushTime", time[1])
                    .orderByDesc("orderPushTime");
        } else {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(pushId), "orderPushUserId", pushId)
                    .eq(EmptyChecker.notEmpty(saleId), "orderSalesUserId", saleId)
                    .eq(EmptyChecker.notEmpty(orderStaticId), "orderStaticId", orderStaticId).orderByDesc("orderPushTime");
        }

        long total = 5; //总记录数
        long pages = 1; //总页数
        Map<String, Object> map = new HashMap<>();
        List<Map> orderTypeOneNameMap = new ArrayList<>();
        List<Map> pushUserMap = new ArrayList<>();
        List<Map> saleUserMap = new ArrayList<>();

        if (EmptyChecker.notEmpty(NameCode)) {
            // 有摘要，只有一个返回值
            List<Order> orders = orderMapper.selectList(queryWrapper);
            for (Order order : orders) {
                if (NameCode.contains(order.getOrderCode())) {
                    String replace = NameCode.replace(order.getOrderCode(), "");
                    if (replace.equals(order.getOrderName())) {
                        List<Order> orderList = new ArrayList();
                        orderList.add(order);
                        // 说明是这个
                        // 获取大类别名称
                        Goodstypeone oneOne = goodstypeoneService.getOneOne(order.getOrderTypeOneId());
                        Map<String, String> OneNameTemp = new HashMap<>();
                        OneNameTemp.put("orderTypeOneName", oneOne.getGoodsTypeOneName());
                        orderTypeOneNameMap.add(OneNameTemp);
                        // 获取入库人员
                        Map<String, String> pushUserTemp = new HashMap<>();
                        if (EmptyChecker.notEmpty(order.getOrderPushUserId())) {
                            pushUserTemp.put("pushUserTemp", userService.getUserNameById(order.getOrderPushUserId().toString()));
                            pushUserMap.add(pushUserTemp);
                        }
                        // 获取出库人员
                        Map<String, String> saleUserTemp = new HashMap<>();
                        if (EmptyChecker.notEmpty(order.getOrderSalesUserId())) {
                            saleUserTemp.put("saleUserTemp", userService.getUserNameById(order.getOrderSalesUserId().toString()));
                            saleUserMap.add(saleUserTemp);
                        }
                        map.put("data", orderList);
                        map.put("total", total);
                        map.put("pages", pages);
                        map.put("orderTypeOneNameMap", orderTypeOneNameMap);
                        map.put("pushUserMap", pushUserMap);
                        map.put("saleUserMap", saleUserMap);
                    }
                }
            }
        } else {
            // 没有摘要，正常返回
            //创建Page对象
            //  current是在第几页
            //  limit是一页有几个
            Page<Order> eduTeacherPage = new Page<>(current, limit);

            //调用mybatis plus分页方法进行查询
            orderMapper.selectPage(eduTeacherPage, queryWrapper);

            //通过Page对象获取分页信息
            List<Order> records = eduTeacherPage.getRecords(); //每页的数据 list集合
            total = eduTeacherPage.getTotal(); //总记录数
            pages = eduTeacherPage.getPages(); //总页数

            for (Order record : records) {
                // 获取大类别名称
                Goodstypeone oneOne = goodstypeoneService.getOneOne(record.getOrderTypeOneId());
                Map<String, String> OneNameTemp = new HashMap<>();
                OneNameTemp.put("orderTypeOneName", oneOne.getGoodsTypeOneName());
                orderTypeOneNameMap.add(OneNameTemp);
                // 获取入库人员
                Map<String, String> pushUserTemp = new HashMap<>();
                if (EmptyChecker.notEmpty(record.getOrderPushUserId())) {
                    pushUserTemp.put("pushUserTemp", userService.getUserNameById(record.getOrderPushUserId().toString()));
                    pushUserMap.add(pushUserTemp);
                }
                // 获取出库人员
                Map<String, String> saleUserTemp = new HashMap<>();
                if (EmptyChecker.notEmpty(record.getOrderSalesUserId())) {
                    saleUserTemp.put("saleUserTemp", userService.getUserNameById(record.getOrderSalesUserId().toString()));
                    saleUserMap.add(saleUserTemp);
                }
            }

            map.put("data", records);
            map.put("total", total);
            map.put("pages", pages);
            map.put("orderTypeOneNameMap", orderTypeOneNameMap);
            map.put("pushUserMap", pushUserMap);
            map.put("saleUserMap", saleUserMap);
        }


        if (total == 0) {
            return new ResponseResult(202, "暂无数据");
        }
        return new ResponseResult(200, "查询成功", map);
    }

    //    @ApiOperation("修改卖价")
    public ResponseResult updateOrderPrice(Order order) {
        // 需要重新计算卖价
        order.setOrderProfitPrice(order.getOrderSellingPrice().subtract(order.getOrderCostPrice()));

        int insert = orderMapper.updateById(order);
        if (insert > 0) {
            // 判断是否已结，已结就重新修改利润
            if (order.getOrderStaticId() != 1) return new ResponseResult(200, "修改成功");
            Boolean aBoolean = false;
            List<Profit> profits = profitMapper.selectList(null);
            for (Profit profit : profits) {
                if (profit.getProfitName().equals(order.getOrderName() + order.getOrderCode())) {
                    if (profit.getProfitType().equals("销售回收")) {
                        // 回收和销售是一个人  总利润*20%      order.getOrderProfitPrice()是总利润
                        BigDecimal divide = order.getOrderProfitPrice().multiply(new BigDecimal("0.20"));
                        profit.setProfitPrice(divide);
                        // 个人业绩,是一个人  总利润*100%
                        profit.setProfitOutStanding(order.getOrderProfitPrice().toString());
                        aBoolean = profitService.updateProfit(profit);
                    } else {
                        // 回收和销售是两个人  总利润*20%/2     order.getOrderProfitPrice()是总利润
                        BigDecimal divide = order.getOrderProfitPrice().multiply(new BigDecimal("0.20")).divide(new BigDecimal("2"));
                        profit.setProfitPrice(divide);
                        // 个人业绩,是两个人  总利润*50%
                        profit.setProfitOutStanding(order.getOrderProfitPrice().multiply(new BigDecimal("0.50")).toString());
                        aBoolean = profitService.updateProfit(profit);
                    }
                }
            }
            if (aBoolean) return new ResponseResult(200, "修改成功");
            return new ResponseResult(200, "修改成功");
        }
        return new ResponseResult(200, "修改失败");
    }

    //    @ApiOperation("退单")
    public ResponseResult deleteOrder(String id) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("orderId", id));
        // 修改订单状态
        order.setOrderStaticId(3);
        // 修改商品状态
        Goods goods = goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goodsId", order.getGoodsId()));
        goods.setGoodsDisabled("");
        int i = goodsMapper.updateById(goods);
        int insert = orderMapper.updateById(order);
        if (insert > 0 && i > 0) {
            //  删除员工的这行工资
            List<Profit> profits = profitMapper.selectList(null);
            for (Profit profit : profits) {
                if (profit.getProfitName().equals(order.getOrderName() + order.getOrderCode())) {
                    if (profit.getProfitUserId().equals(order.getOrderPushUserId())) {
                        //  回收人员
                       profitService.deleteProfit(profit.getProfitId());
                    }
                    if (profit.getProfitUserId().equals(order.getOrderSalesUserId())) {
                        //  销售人员
                         profitService.deleteProfit(profit.getProfitId());
                    }
                }
            }
            //  加利润
            //  财务变动
            Finance finance = new Finance();
            finance.setFinanceType("支出");
            finance.setIncomeId(3);
            finance.setFinanceBalance("-" + order.getOrderSellingPrice());
            //  退单时间应该是当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();
            // 定义要使用的格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 格式化当前时间为字符串
            String formattedDateTimeString = currentDateTime.format(formatter);
            // 将格式化后的字符串转换回 LocalDateTime
            LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTimeString, formatter);
            finance.setFinanceTime(parsedDateTime);
            finance.setFinanceName(goods.getGoodsName() + goods.getGoodsCode());
            finance.setFinanceUserId(order.getOrderSalesUserId());
            financeService.addFinance(finance);
            return new ResponseResult(200, "修改成功");
        }
        return new ResponseResult(202, "修改失败");
    }

    //    @ApiOperation("修改未结为已结")
    public ResponseResult updateOrderState(String id) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("orderId", id));
        if (order.getOrderStaticId() == 2) {
            order.setOrderStaticId(1);
            int insert = orderMapper.updateById(order);
            Goods goods = goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goodsId", order.getGoodsId()));
            //  计算利润
            Boolean goodsId = addProfit(order, goods);

            //  加利润
            //  财务变动
            Finance finance = new Finance();
            finance.setFinanceType("收入");
            finance.setIncomeId(1);
            BigDecimal priceTotal = order.getOrderSellingPrice();
            finance.setFinanceBalance("+" + priceTotal);
            //  退单时间应该是当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();
            // 定义要使用的格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 格式化当前时间为字符串
            String formattedDateTimeString = currentDateTime.format(formatter);
            // 将格式化后的字符串转换回 LocalDateTime
            LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTimeString, formatter);
            finance.setFinanceTime(parsedDateTime);
            finance.setFinanceName(goods.getGoodsName() + goods.getGoodsCode());
            finance.setFinanceUserId(order.getOrderSalesUserId());
            financeService.addFinance(finance);

            if (insert > 0) {
                return new ResponseResult(200, "修改成功");
            }
            return new ResponseResult(202, "修改失败");
        }
        return new ResponseResult(202, "修改失败,该商品不是未结状态");

    }

}
