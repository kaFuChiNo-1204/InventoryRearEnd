package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.User;
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
 * @since 2023-10-19
 */
@Api(tags = "员工管理模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("selectNameAll")
    @ApiOperation("获取所有员工姓名")
    private ResponseResult selectUserName() {
        return userService.selectName();
    }

    @GetMapping("selectAllBy")
    @ApiOperation("根据条件获取员工")
    private ResponseResult selectListUserBy(String name
                                     , Long current, Long limit) {
//        System.out.println(name);
        return userService.selectListUserBy(name, current, limit);
    }

    @PostMapping("addUser")
    @ApiOperation("添加业主信息")
    private ResponseResult addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("updateUser")
    @ApiOperation("根据id修改业主信息")
    private ResponseResult updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("deleteUserById")
    @ApiOperation("根据id删除业主信息")
    private ResponseResult deleteUserById(@RequestBody User user) {
        return userService.deleteUserById(user.getUserId());
    }


    @PostMapping("getUserNameById")
    @ApiOperation("根据id获取业主姓名")
    private ResponseResult getUserNameById(String id) {
        return new ResponseResult(200,userService.getUserNameById(id)) ;
    }

}
