package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.UserMapper;
import com.housekeeperispurchase.pojo.*;
import com.housekeeperispurchase.mapper.GoodsMapper;
import com.housekeeperispurchase.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AdditiveServiceImpl additiveService;

    @Autowired
    GoodstypeoneServiceImpl goodstypeoneService;

    @Autowired
    FinanceServiceImpl financeService;


    //获取所有的库存摘要
    @Override
    public ResponseResult getGoodsNameCode() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>().ne("goodsDisabled", "出库");
        queryWrapper.select("goodsName", "goodsCode");
        List<Goods> goods1 = goodsMapper.selectList(queryWrapper);
        List<String> list1 = new ArrayList<>();
        for (Goods o : goods1) {
            list1.add(o.getGoodsName() + o.getGoodsCode());
        }
        return new ResponseResult(200, "查询成功", list1);
    }

    //获取所有的库存摘要
    @Override
    public ResponseResult getGoodsNameCodesetOrder() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>().notIn("goodsDisabled", "出库", "借出");
        queryWrapper.select("goodsName", "goodsCode");
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        System.out.println(goods);
        List<String> list1 = new ArrayList<>();
        for (Goods o : goods) {
            list1.add(o.getGoodsName() + o.getGoodsCode());
        }
        return new ResponseResult(200, "查询成功", list1);
    }

    //更加库存摘要获取库存信息
    @Override
    public ResponseResult getGoodsByGoodsNameCode(String NameCode) {
        // 先获取所有的库存code
        List<Goods> goodsCode = goodsMapper.selectList(null);
        Goods goods1 = new Goods();
        for (Goods goods : goodsCode) {
            if (NameCode.contains(goods.getGoodsCode())) {
                String replace = NameCode.replace(goods.getGoodsCode(), "");
                if (replace.equals(goods.getGoodsName())) {
                    // 说明是这个
                    goods1 = goods;
                }
            }
        }
        if (EmptyChecker.notEmpty(goods1.getGoodsId())) {
            return new ResponseResult(200, "查询成功", goods1);
        }
        return new ResponseResult(200, "查询失败");

    }

    @Override
    public ResponseResult getPushUserName() {
//        return goodsService.selectName();
//        @returns {id+name}
        return null;
    }

    //    获取所有的库存内容
    @Override
    public ResponseResult getGoodsData(String NameCode, String goodsPushUserId, Long current, Long limit) {
        QueryWrapper queryWrapper = new QueryWrapper<Goods>()
                .eq(EmptyChecker.notEmpty(goodsPushUserId), "goodsPushUserId", goodsPushUserId)
                .ne("goodsDisabled", "出库").orderByDesc("goodsTime");;

        List<Map> nameMap = new ArrayList<>();
        List<Map> priceMap = new ArrayList<>();
        List<Map> goodsTypeOneNameMap = new ArrayList<>();
        List<Map> additivesList = new ArrayList<>();
        long total = 5; //总记录数
        long pages = 1; //总页数
        Map<String, Object> map = new HashMap<>();

        if (EmptyChecker.notEmpty(NameCode)) {
            // 有摘要，只有一个返回值
            List<Goods> goodsCode = goodsMapper.selectList(queryWrapper);
            for (Goods goods : goodsCode) {
                if (NameCode.contains(goods.getGoodsCode())) {
                    String replace = NameCode.replace(goods.getGoodsCode(), "");
                    if (replace.equals(goods.getGoodsName())) {
                        List<Goods> goodsList = new ArrayList();
                        goodsList.add(goods);
                        // 说明是这个
                        // 获取价格
                        String userNameById = userService.getUserNameById(goods.getGoodsPushUserId().toString());
                        Map<String, Object> mapTemp = new HashMap<>();
                        mapTemp.put("userName", userNameById);
                        nameMap.add(mapTemp);
                        // 获取成本
                        int additivePriceTotal = additiveService.getAdditivePriceTotal(goods.getAdditiveId());
                        Map<String, Integer> priceTemp = new HashMap<>();
                        priceTemp.put("PriceTotal", additivePriceTotal);
                        priceMap.add(priceTemp);
                        // 获取大类别名称
                        Goodstypeone oneOne = goodstypeoneService.getOneOne(goods.getGoodsTypeOneId());
                        Map<String, String> OneNameTemp = new HashMap<>();
                        OneNameTemp.put("goodsTypeOneName", oneOne.getGoodsTypeOneName());
                        goodsTypeOneNameMap.add(OneNameTemp);
                        // 获取附加成本
                        Map<String, List<Additive>> additiveTemp = new HashMap<>();
                        if (EmptyChecker.notEmpty(goods.getAdditiveId())) {
                            additiveTemp.put("additivesList", additiveService.AdditiveById(goods.getAdditiveId()));
                            additivesList.add(additiveTemp);
                        }
                        map.put("data", goodsList);
                        map.put("total", total);
                        map.put("pages", pages);
                        map.put("nameMap", nameMap);
                        map.put("priceMap", priceMap);
                        map.put("goodsTypeOneNameMap", goodsTypeOneNameMap);
                        map.put("additivesList", additivesList);
                    }
                }
            }
        } else {
            // 没有摘要，正常返回
            //  current是在第几页
            //  limit是一页有几个
            Page<Goods> eduTeacherPage = new Page<>(current, limit);
            //调用mybatis plus分页方法进行查询
            goodsMapper.selectPage(eduTeacherPage, queryWrapper);
            //通过Page对象获取分页信息
            List<Goods> records = eduTeacherPage.getRecords(); //每页的数据 list集合
            total = eduTeacherPage.getTotal(); //总记录数
            pages = eduTeacherPage.getPages(); //总页数
            for (Goods record : records) {
                // 获取价格
                String userNameById = userService.getUserNameById(record.getGoodsPushUserId().toString());
                Map<String, Object> mapTemp = new HashMap<>();
                mapTemp.put("userName", userNameById);
                nameMap.add(mapTemp);
                // 获取成本
                int additivePriceTotal = additiveService.getAdditivePriceTotal(record.getAdditiveId());
                Map<String, Integer> priceTemp = new HashMap<>();
                priceTemp.put("PriceTotal", additivePriceTotal);
                priceMap.add(priceTemp);
                // 获取大类别名称
                Goodstypeone oneOne = goodstypeoneService.getOneOne(record.getGoodsTypeOneId());
                Map<String, String> OneNameTemp = new HashMap<>();
                OneNameTemp.put("goodsTypeOneName", oneOne.getGoodsTypeOneName());
                goodsTypeOneNameMap.add(OneNameTemp);
                // 获取附加成本
                Map<String, List<Additive>> additiveTemp = new HashMap<>();
                if (EmptyChecker.notEmpty(record.getAdditiveId())) {
                    additiveTemp.put("additivesList", additiveService.AdditiveById(record.getAdditiveId()));
                    additivesList.add(additiveTemp);
                } else {
                    // 加空的占位
                    List<Additive> list = new ArrayList<>();
                    additiveTemp.put("additivesList", list);
                    additivesList.add(additiveTemp);
                }
            }
            System.out.println("获取的库存信息:"+records);
            map.put("data", records);
            map.put("total", total);
            map.put("pages", pages);
            map.put("nameMap", nameMap);
            map.put("priceMap", priceMap);
            map.put("goodsTypeOneNameMap", goodsTypeOneNameMap);
            map.put("additivesList", additivesList);
        }

        if (total == 0) {
            return new ResponseResult(202, "暂无数据");
        }
        return new ResponseResult(200, "查询成功", map);
    }

    //    添加库存
    @Override
    public ResponseResult addGoodsRow(@RequestBody Goods goods) {
        // 编码不能重复
        Goods goods1 = goodsMapper.selectOne(new QueryWrapper<Goods>().eq(EmptyChecker.notEmpty(goods.getGoodsCode()), "goodsCode", goods.getGoodsCode()));
        if (EmptyChecker.notEmpty(goods1)) {
            return new ResponseResult(202, "添加失败,编码重复");
        }
        goods.setGoodsCode(goods.getGoodsCode().replaceAll(" ",""));
        goods.setGoodsName(goods.getGoodsName().replaceAll(" ",""));
        int insert = goodsMapper.insert(goods);
        if (insert > 0) {
            //  财务变动
            Finance finance = new Finance();
            finance.setFinanceType("支出");
            finance.setIncomeId(2);
            BigDecimal priceTotal = goods.getGoodsPushPrice();
            if (EmptyChecker.notEmpty(goods.getAdditiveId())) {
                List<Additive> list = additiveService.AdditiveById(goods.getAdditiveId());
                for (Additive additive : list) {
                    priceTotal = priceTotal.add(new BigDecimal(additive.getAdditivePrice()));
                }
            }
            finance.setFinanceBalance("-" + priceTotal);
            finance.setFinanceTime(goods.getGoodsTime());
            finance.setFinanceName(goods.getGoodsName()+goods.getGoodsCode());
            finance.setFinanceUserId(goods.getGoodsPushUserId());
            financeService.addFinance(finance);

            return new ResponseResult(200, "添加成功");
        }
        return new ResponseResult(202, "添加失败");
    }

    //    修改库存内容
    @Override
    public ResponseResult updateGoods(Goods goods) {
        goods.setGoodsCode(goods.getGoodsCode().replaceAll(" ",""));
        goods.setGoodsName(goods.getGoodsName().replaceAll(" ",""));
        int i = goodsMapper.updateById(goods);
        if (i > 0) {
            return new ResponseResult(200, "修改成功");
        }
        return new ResponseResult(202, "修改失败");
    }

    //    借出库存
    @Override
    public ResponseResult setGoodsNote(int id) {
        Goods goods = goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goodsId", id));
        if (EmptyChecker.notEmpty(goods.getGoodsId())) {
            if (goods.getGoodsDisabled().equals("借出")) {
                goods.setGoodsDisabled("");
            } else {
                goods.setGoodsDisabled("借出");
            }
            int i = goodsMapper.updateById(goods);
            if (i > 0) {
                return new ResponseResult(200, "修改成功");
            }
        }
        return new ResponseResult(202, "修改失败");
    }


}
