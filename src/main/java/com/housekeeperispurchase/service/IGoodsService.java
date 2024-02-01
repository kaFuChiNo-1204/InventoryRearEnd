package com.housekeeperispurchase.service;

import com.housekeeperispurchase.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.housekeeperispurchase.utils.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
public interface IGoodsService extends IService<Goods> {

    ResponseResult getGoodsNameCode();

    //获取所有的库存摘要
    ResponseResult getGoodsNameCodesetOrder();

    //获取所有的库存摘要
    ResponseResult getGoodsByGoodsNameCode(String NameCode);

    ResponseResult getPushUserName();

    //    获取所有的库存内容
    ResponseResult getGoodsData(String NameCode, String goodsPushUserId, Long current, Long limit);

    ResponseResult addGoodsRow(@RequestBody Goods goods);

    ResponseResult updateGoods(@RequestBody Goods goods);

    ResponseResult setGoodsNote(int id);
}
