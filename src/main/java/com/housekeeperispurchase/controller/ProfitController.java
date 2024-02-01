package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.service.impl.OrderServiceImpl;
import com.housekeeperispurchase.service.impl.ProfitServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Api(tags = "工资管理模块")
@RestController
@RequestMapping("/profit")
public class ProfitController {

    @Autowired
    private ProfitServiceImpl profitService;


    @GetMapping("getOrderData")
    @ApiOperation("获取工资内容")
    private ResponseResult getProfitData(String profitUserId, String profitType,  String[] profitTime, Long current, Long limit) {
        return profitService.getProfitData(profitUserId, profitType, profitTime,  current, limit);
    }

}
