package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Goods;
import com.housekeeperispurchase.service.impl.GoodsServiceImpl;
import com.housekeeperispurchase.service.impl.UserServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Api(tags = "库存管理模块")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsServiceImpl goodsService;

    @GetMapping("getGoodsNameCode")
    @ApiOperation("获取所有的库存摘要")
    private ResponseResult getGoodsNameCode() {
        return goodsService.getGoodsNameCode();
    }

    @GetMapping("getGoodsNameCodesetOrder")
    @ApiOperation("获取所有的可以出库的库存摘要")
    private ResponseResult getGoodsNameCodesetOrder() {
        return goodsService.getGoodsNameCodesetOrder();
    }

    @GetMapping("getGoodsByGoodsNameCode")
    @ApiOperation("根据库存摘要获取库存ID")
    private ResponseResult getGoodsByGoodsNameCode(String NameCode) {
        return goodsService.getGoodsByGoodsNameCode(NameCode);
    }

    @GetMapping("getPushUserName")
    @ApiOperation("获取所有的入库人员")
    private ResponseResult getPushUserName() {
//        return goodsService.selectName();
//        @returns {id+name}
        return null;
    }
    @GetMapping("getGoodsData")
    @ApiOperation("获取库存内容")
    private ResponseResult getGoodsData(String NameCode,String goodsPushUserId ,Long current, Long limit) {
        return goodsService.getGoodsData(NameCode,goodsPushUserId,current,limit);
    }

    @PostMapping("addGoodsRow")
    @ApiOperation("添加库存")
    private ResponseResult addGoodsRow(@RequestBody Goods goods) {
        return goodsService.addGoodsRow(goods);
    }

    @PostMapping("updateGoods")
    @ApiOperation("修改某条库存内容")
    private ResponseResult updateGoods(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods);
    }

    @GetMapping("setGoodsNote")
    @ApiOperation("借出某条库存内容")
    private ResponseResult setGoodsNote(int id) {
//        System.out.println(id);
        return goodsService.setGoodsNote(id);
    }


}
