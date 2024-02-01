package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.OrderMapper;
import com.housekeeperispurchase.pojo.Goods;
import com.housekeeperispurchase.pojo.Goodstypeone;
import com.housekeeperispurchase.pojo.Order;
import com.housekeeperispurchase.pojo.Profit;
import com.housekeeperispurchase.mapper.ProfitMapper;
import com.housekeeperispurchase.service.IProfitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
public class ProfitServiceImpl extends ServiceImpl<ProfitMapper, Profit> implements IProfitService {


    @Autowired
    private ProfitMapper profitMapper;
    @Autowired
    private UserServiceImpl userService;


    //  获取工资内容
    public ResponseResult getProfitData(String profitUserId, String profitType, String[] profitTime, Long current, Long limit) {
        // Step1：创建一个 QueryWrapper 对象
        QueryWrapper queryWrapper = new QueryWrapper<Profit>();
        // Step2： 构造查询条件
        if (EmptyChecker.isEmpty(profitTime)) {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(profitUserId), "profitUserId", profitUserId)
                    .eq(EmptyChecker.notEmpty(profitType), "profitType", profitType)
                    .ge(EmptyChecker.isEmpty(profitTime), "profitTime", profitTime[0])
                    .le(EmptyChecker.isEmpty(profitTime), "profitTime", profitTime[1])
                    .orderByDesc("profitId");
        } else {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(profitUserId), "profitUserId", profitUserId)
                    .eq(EmptyChecker.notEmpty(profitType), "profitType", profitType)
                    .orderByDesc("profitId");
        }
        //创建Page对象
        //  current是在第几页
        //  limit是一页有几个
        Page<Profit> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        profitMapper.selectPage(eduTeacherPage, queryWrapper);
        //通过Page对象获取分页信息
        List<Profit> records = eduTeacherPage.getRecords(); //每页的数据 list集合
        Map<String, Object> map = new HashMap<>();
        long total = eduTeacherPage.getTotal(); //总记录数
        long pages = eduTeacherPage.getPages(); //总页数

        List<Map> UserNameMap = new ArrayList<>();
        for (Profit record : records) {
            // 获取人员
            Map<String, String> pushUserTemp = new HashMap<>();
            if (EmptyChecker.notEmpty(record.getProfitUserId())) {
                pushUserTemp.put("userName", userService.getUserNameById(record.getProfitUserId().toString()));
                UserNameMap.add(pushUserTemp);
            }
        }

        map.put("data", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("UserNameMap", UserNameMap);
        if (total == 0) {
            return new ResponseResult(202, "暂无数据");
        }
        return new ResponseResult(200, "查询成功", map);

    }

    //  添加工资内容
    public Boolean addProfit(Profit profit) {
        // 判断利润不能为负数
        if (profit.getProfitPrice().compareTo(BigDecimal.ZERO) > 0 && Double.parseDouble(profit.getProfitOutStanding()) > 0.00) {
            int insert = profitMapper.insert(profit);
            if (insert > 0) return true;
        }
        return false;
    }

    //  删除工资内容
    public Boolean deleteProfit(int id) {
        int insert = profitMapper.deleteById(id);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    // 修改工资内容
    public Boolean updateProfit(Profit profit) {
        int insert = profitMapper.updateById(profit);
        if (insert > 0) {
            return true;
        }
        return false;
    }
}
