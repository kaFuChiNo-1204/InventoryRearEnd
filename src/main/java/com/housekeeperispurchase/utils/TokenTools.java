package com.housekeeperispurchase.utils;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * 使用token的工具类
 */
public class TokenTools {
    /**
     * 生成token放入session
     *
     * @param session
     * @param tokenServerkey
     */

    public static void createToken(HttpSession session, String tokenServerkey) {
        String token = TokenProccessor.getInstance().makeToken();
        session.setAttribute(tokenServerkey, token);
    }


    /**
     * 移除token
     *
     * @param session
     * @param tokenServerkey
     */

    public static void removeToken(HttpSession session, String tokenServerkey) {
        session.removeAttribute(tokenServerkey);
    }


    /**
     * 判断请求参数中的token是否和session中一致
     *
     * @param session
     * @param tokenClientkey
     * @param tokenServerkey
     * @return
     */

    public static boolean judgeTokenIsEqual(HttpSession session, String tokenClientkey, String tokenServerkey) {
//        String token_client = session.getParameter(tokenClientkey);
        String token_client = tokenClientkey;
        if (EmptyChecker.isEmpty(token_client)) {
            return false;
        }

        String token_server = (String) session.getAttribute(tokenServerkey);
        if (EmptyChecker.isEmpty(token_server)) {
            return false;
        }
        if (!token_server.equals(token_client)) {
            return false;
        }
        return true;
    }

    /**
     * 判断这个token是否存在，存在就返回用户名(表示还没有退出登录),不存在就返回null(表示已经退出登录了)
     *
     * @param session
     * @param token
     * @return
     */
    public static String selectTokenByUsarName(HttpSession session, String token) {
        System.out.println("前端返回的token" + token);
        //获取session
//        HttpSession session = session.getSession();
        // 获取session中所有的键值
        Enumeration<String> attrs = session.getAttributeNames();
        // 遍历attrs中的
        while (attrs.hasMoreElements()) {
            // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            String vakue = (String) session.getAttribute(name);
            System.out.println("session还存在的值" + name + "----" + vakue);
            // 打印结果
            if (EmptyChecker.notEmpty(token) && token.equals(vakue)) {
                // 说明该token还在登录中,返回用户名
                return name;
            }
        }
        // 说明这个用户已经退出登录,返回空
        return "-1";
    }
}