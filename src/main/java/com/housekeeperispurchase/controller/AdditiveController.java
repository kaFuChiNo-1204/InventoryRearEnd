package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Additive;
import com.housekeeperispurchase.pojo.Goods;
import com.housekeeperispurchase.service.impl.AdditiveServiceImpl;
import com.housekeeperispurchase.service.impl.UserServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-10-24
 */
@Api(tags = "附加成本管理模块")
@RestController
@RequestMapping("/additive")
public class AdditiveController {

    @Autowired
    private AdditiveServiceImpl additiveService;

    @PostMapping("addAdditive")
    @ApiOperation("添加附加成本")
    private ResponseResult addAdditive(@RequestBody Additive [] additiveList) {
        return additiveService.addAdditive(additiveList);
    }

    @GetMapping("AdditiveById")
    @ApiOperation("根据id查询附加成本")
    private ResponseResult AdditiveById(String id) {
        return new ResponseResult(200, "", additiveService.AdditiveById(id));

    }

    @PostMapping("updateAdditive")
    @ApiOperation("修改附加成本")
    private ResponseResult updateAdditive(@RequestBody Additive additive) {
        return additiveService.updateAdditive(additive);
    }

    @GetMapping("deleteAdditiveById")
    @ApiOperation("删除附加成本")
    private ResponseResult deleteAdditiveById(int  id) {
        return additiveService.deleteAdditiveById(id);
    }
}
