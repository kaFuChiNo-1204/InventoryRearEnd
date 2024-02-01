package com.housekeeperispurchase.service.impl;

import com.housekeeperispurchase.pojo.Account;
import com.housekeeperispurchase.mapper.AccountMapper;
import com.housekeeperispurchase.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import com.housekeeperispurchase.utils.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private HttpSession sessions;

    /**
     * 后台的登录的API
     *
     * @param username
     * @param password
     * @return
     */
    public ResponseResult houLogin(HttpSession session, String username, String password) {
        final List<Account> auusers =accountMapper.selectList(null);
        for (Account auuser : auusers) {
            if (auuser.getAccountUser().equals(username) && auuser.getAccountPass().equals(password)) {
                // 说明账号密码正确
                // 创建并存储token在后端Session中，名字就是账号名(auuser.getAuUserAcc())
                TokenTools.createToken(session, auuser.getAccountUser());
                // 获取到存储的token值,并且将值返回给前台
                String token_server = (String) session.getAttribute(auuser.getAccountUser());
                sessions = session;
                return new ResponseResult(200, "登录成功", token_server);
            }
        }
        return new ResponseResult(202, "登录失败,账号或者密码错误");
    }

    /**
     * 后台退出登录
     *
     * @param session
     * @param username
     * @return
     */
    public ResponseResult deleHouLogin(HttpSession session, String username) {
        TokenTools.removeToken(sessions, username);
        return new ResponseResult(200, "已退出登录");
    }

    /**
     * 根据后台传过来的token来返回token所对应的数据
     *
     * @param session
     * @param token
     * @return
     */
    public ResponseResult tokenHouLogin(HttpSession session, String token) {
        // 获取到这个token是否还存在
        String s = TokenTools.selectTokenByUsarName(sessions, token);
        if (s.equals("-1")) {
            // 说明已经退出登录了
            return new ResponseResult(202, "身份信息已失效,请重新登录");
        } else {
            // 说明还在登录状态中
            final List<Account> auusers =accountMapper.selectList(null);
            for (Account auuser : auusers) {
                if (s.equals(auuser.getAccountUser())) {
                    // 根据账号名字获取到账号信息

                    Map<String, Object> map = new HashMap<>();
                    // 存放根据权限名字获取到的权限的数据
//                    StringBuilder auroles = new StringBuilder();
//                    if (EmptyChecker.notEmpty(auuser.getAuRoleIds())) {
//                        final String[] split = auuser.getAuRoleIds().split(",");
//                        for (int i = 0; i < split.length; i++) {
//                            // 根据权限id获取到权限的数据
//                            Aurole aurole = auroleService.selectAurById(Integer.parseInt(split[i]));
//                            if (EmptyChecker.notEmpty(aurole)) {
//                                // 如果不为空,就将权限内容加在后面
//                                auroles.append(aurole.getAuRoleContent());
//                            }
//                        }
//                    }
//                    map.put("auroles", auroles.toString());
                    map.put("data", auuser);
                    return ResponseResult.success(map);
                }
            }
            return new ResponseResult(202, "未知错误");
        }
    }

}
