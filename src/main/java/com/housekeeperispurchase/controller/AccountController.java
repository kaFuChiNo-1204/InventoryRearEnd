package com.housekeeperispurchase.controller;


import com.housekeeperispurchase.pojo.Account;
import com.housekeeperispurchase.service.impl.AccountServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Api(tags = "账号密码")
@RestController
@RequestMapping("/login")
@ResponseBody
public class AccountController {


    @Autowired
    private AccountServiceImpl accountService;


    @ApiOperation("后台登录接口")
    @PostMapping("houLogin")
    public ResponseResult houLogin(HttpSession session, @RequestBody Account loginBean) {
        return accountService.houLogin(session, loginBean.getAccountUser(), loginBean.getAccountPass());
    }

    @ApiOperation("后台退出登录接口")
    @GetMapping("deleHouLogin")
    public ResponseResult deleHouLogin(HttpSession session, String username) {
        System.out.println(username);
        return accountService.deleHouLogin(session, username);
    }

    @ApiOperation("后台根据token返回信息")
    @GetMapping("tokenHouLogin")
    public ResponseResult tokenHouLogin(HttpServletRequest request, HttpSession session) {
        //取出请求头中的token
        String X_Token = request.getHeader("X-Token");
        return accountService.tokenHouLogin(session, X_Token);
    }
}
