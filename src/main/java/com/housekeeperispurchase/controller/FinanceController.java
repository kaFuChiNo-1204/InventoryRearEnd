package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Finance;
import com.housekeeperispurchase.service.impl.FinanceServiceImpl;
import com.housekeeperispurchase.service.impl.GoodsServiceImpl;
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
@Api(tags = "财务管理模块")
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceServiceImpl financeService;

    @GetMapping("getFinanceNameCode")
    @ApiOperation("获取财务摘要")
    private ResponseResult getFinanceNameCode() {
        return financeService.getFinanceNameCode();
    }


    @GetMapping("getFinance")
    @ApiOperation("获取所有的财务内容")
    private ResponseResult getFinance(String NameCode, String financeType, String[] time, Long current, Long limit) {
        return financeService.getFinance(NameCode, financeType, time, current, limit);
    }

    @PostMapping("addFinance")
    @ApiOperation("新增财务内容")
    private ResponseResult addFinance(@RequestBody Finance finance) {
        Boolean aBoolean = financeService.addFinance(finance);
        if (aBoolean) return new ResponseResult(200, "新增成功");
        return new ResponseResult(200, "新增失败");
    }
}
