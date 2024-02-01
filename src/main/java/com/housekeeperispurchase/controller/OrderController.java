package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Goods;
import com.housekeeperispurchase.pojo.Order;
import com.housekeeperispurchase.service.impl.GoodsServiceImpl;
import com.housekeeperispurchase.service.impl.OrderServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Api(tags = "订单管理模块")
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("getOrderNameCode")
    @ApiOperation("获取所有的订单摘要")
    private ResponseResult getOrderNameCode() {
        return orderService.getOrderNameCode();
    }

    @GetMapping("getOrderPushUser")
    @ApiOperation("获取所有入库人员")
    private ResponseResult getOrderPushUser() {
        return orderService.getOrderPushUser();
    }

    @GetMapping("getOrderSalesCode")
    @ApiOperation("获取所有出库人员")
    private ResponseResult getOrderSalesCode() {
        return orderService.getOrderSalesCode();
    }


    @PostMapping("addOrder")
    @ApiOperation("新增订单")
    private ResponseResult addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("getOrderData")
    @ApiOperation("获取订单内容")
    private ResponseResult getOrderData(String NameCode, String pushId, String saleId, String orderStaticId, String[] time, Long current, Long limit) {
        return orderService.getOrderData(NameCode, pushId, saleId, orderStaticId, time, current, limit);
    }

    @PostMapping("updateOrderPrice")
    @ApiOperation("修改卖价")
    private ResponseResult updateOrderPrice(@RequestBody Order order) {
        return orderService.updateOrderPrice(order);
    }

    @GetMapping("deleteOrder")
    @ApiOperation("退单")
    private ResponseResult deleteOrder(String id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("updateOrderState")
    @ApiOperation("未结变已结")
    private ResponseResult updateOrderState(String id) {
        return orderService.updateOrderState(id);
    }

}
