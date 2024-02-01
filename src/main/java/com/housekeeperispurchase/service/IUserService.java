package com.housekeeperispurchase.service;

import com.housekeeperispurchase.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.housekeeperispurchase.utils.ResponseResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
public interface IUserService extends IService<User> {

    ResponseResult selectName();

}
