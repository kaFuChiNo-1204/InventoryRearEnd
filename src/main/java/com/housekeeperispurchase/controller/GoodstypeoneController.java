package com.housekeeperispurchase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.housekeeperispurchase.pojo.Goodstypeone;
import com.housekeeperispurchase.pojo.Goodstypethree;
import com.housekeeperispurchase.pojo.Goodstypetwo;
import com.housekeeperispurchase.service.impl.GoodsServiceImpl;
import com.housekeeperispurchase.service.impl.GoodstypeoneServiceImpl;
import com.housekeeperispurchase.service.impl.GoodstypethreeServiceImpl;
import com.housekeeperispurchase.service.impl.GoodstypetwoServiceImpl;
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
@Api(tags = "类别管理模块")
@RestController
@RequestMapping("/goodstypeone")
public class GoodstypeoneController {

    @Autowired
    private GoodstypeoneServiceImpl goodstypeoneService;

    @Autowired
    private GoodstypetwoServiceImpl goodstypetwoService;

    @Autowired
    private GoodstypethreeServiceImpl goodstypethreeService;

    @GetMapping("getOne")
    @ApiOperation("获取所有的一级类别")
    private ResponseResult getOne() {
     return goodstypeoneService.getOne();
    }

    @GetMapping("getTwo")
    @ApiOperation("根据一级id获取所有的二级类别")
    private ResponseResult getTwo(int id ) {
         return goodstypetwoService.getTwo(id);
    }


    @GetMapping("getThree")
    @ApiOperation("根据二级id获取所有的三级类别")
    private ResponseResult getThree(int id) {
         return goodstypethreeService.getThree(id);
    }

    @GetMapping("getOneOne")
    @ApiOperation("根据一级id获取到一级类别")
    private ResponseResult getOneOne(int id) {
        return new ResponseResult(200, "查询成功", goodstypeoneService.getOneOne(id));
    }

    @GetMapping("getTwoOne")
    @ApiOperation("根据二级id获取到二级类别")
    private ResponseResult getTwoOne(int id) {
        return goodstypetwoService.getTwoOne(id);
    }

    @GetMapping("getThreeOne")
    @ApiOperation("根据三级id获取到的三级类别")
    private ResponseResult getThreeOne(int id) {
        return goodstypethreeService.getThreeOne(id);
    }



    @GetMapping("getOneById")
    @ApiOperation("根据id获取一级类别,带分页")
    private ResponseResult getOneById(Long current, Long limit) {
        return goodstypeoneService.getOneById(current,limit);
    }

    @GetMapping("getTwoById")
    @ApiOperation("根据id获取二级类别,带分页")
    private ResponseResult getTwoById(int id,Long current, Long limit) {
        return goodstypetwoService.getTwoById(id,current,limit);
    }

    @GetMapping("getThreeById")
    @ApiOperation("根据id获取三级类别,带分页")
    private ResponseResult getThreeById(int id,Long current, Long limit) {
        return goodstypethreeService.getThreeById(id,current,limit);
    }


    @PostMapping("addOne")
    @ApiOperation("添加一级类别")
    private ResponseResult addOne(@RequestBody Goodstypeone goodstypeone) {
        return goodstypeoneService.addOne(goodstypeone);
    }

    @PostMapping("addTwo")
    @ApiOperation("添加二级类别")
    private ResponseResult addTwo(@RequestBody Goodstypetwo goodstypetwo) {
        return goodstypetwoService.addTwo(goodstypetwo);
    }
    @PostMapping("addThree")
    @ApiOperation("添加三级类别")
    private ResponseResult addThree(@RequestBody Goodstypethree goodstypethree) {
        return goodstypethreeService.addThree(goodstypethree);
    }

    @PostMapping("updateOne")
    @ApiOperation("修改一级类别")
    private ResponseResult updateOne(@RequestBody Goodstypeone goodstypeone) {
        return goodstypeoneService.updateOne(goodstypeone);
    }

    @PostMapping("updateTwo")
    @ApiOperation("修改二级类别")
    private ResponseResult updateTwo(@RequestBody Goodstypetwo goodstypetwo) {
        return goodstypetwoService.updateTwo(goodstypetwo);
    }
    @PostMapping("updateThree")
    @ApiOperation("修改三级类别")
    private ResponseResult updateThree(@RequestBody Goodstypethree goodstypethree) {
        return goodstypethreeService.updateThree(goodstypethree);
    }

    @GetMapping("deleOneById")
    @ApiOperation("根据id删除一级类别")
    private ResponseResult deleOneById(int id) {
        return goodstypeoneService.deleOneById(id);
    }

    @GetMapping("deleTwoById")
    @ApiOperation("根据id删除二级类别")
    private ResponseResult deleTwoById(int id) {
        return goodstypetwoService.deleTwoById(id);
    }
    @GetMapping("deleThreeById")
    @ApiOperation("根据id删除三级类别")
    private ResponseResult deleThreeById(int id) {
        return goodstypethreeService.deleThreeById(id);
    }


}
