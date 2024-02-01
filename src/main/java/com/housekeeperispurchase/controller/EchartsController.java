package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Additive;
import com.housekeeperispurchase.pojo.Finance;
import com.housekeeperispurchase.service.impl.AdditiveServiceImpl;
import com.housekeeperispurchase.service.impl.EchartsServiceImpl;
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
@Api(tags = "数据可视化模块")
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsServiceImpl echartsService;

    @PostMapping("getInit1Data")
    @ApiOperation("获取收支趋势Init1")
    private ResponseResult getInit1Data(String[] time) {
        return echartsService.getInit1Data(time);
    }

    @PostMapping("getInit2Data")
    @ApiOperation("获取收支趋势Init2")
    private ResponseResult getInit2Data(String[] time) {
        return echartsService.getInit2Data(time);
    }

    @PostMapping("getInit3Data")
    @ApiOperation("获取收支趋势Init3")
    private ResponseResult getInit3Data(String[] time) {
        return echartsService.getInit3Data(time);
    }

    @PostMapping("getInit4Data")
    @ApiOperation("获取收支趋势Init4")
    private ResponseResult getInit4Data(String[] time) {
        return echartsService.getInit4Data(time);
    }

    @PostMapping("getInit5Data")
    @ApiOperation("获取收支趋势Init5")
    private ResponseResult getInit5Data(String[] time) {
        return echartsService.getInit5Data(time);
    }

}
