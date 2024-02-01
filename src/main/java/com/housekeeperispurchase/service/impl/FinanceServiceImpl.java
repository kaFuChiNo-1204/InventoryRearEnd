package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.GoodsMapper;
import com.housekeeperispurchase.pojo.*;
import com.housekeeperispurchase.mapper.FinanceMapper;
import com.housekeeperispurchase.service.IFinanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, Finance> implements IFinanceService {

    @Autowired
    FinanceMapper financeMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    IncomeServiceImpl incomeService;

    //    @ApiOperation("获取财务摘要")
    public ResponseResult getFinanceNameCode() {
        List<Finance> finances = financeMapper.selectList(new QueryWrapper<Finance>().select("financeId", "financeName"));
        return new ResponseResult(200, "查询成功", finances);
    }

    //    @ApiOperation("新增财务内容")
    public Boolean addFinance(Finance finance) {
        System.out.println("finance" + finance);
        int insert = financeMapper.insert(finance);
        if (insert > 0) return true;
        return false;
    }

    //    @ApiOperation("获取所有的财务内容")
    public ResponseResult getFinance(String NameCode, String financeType, String[] time, Long current, Long limit) {

        // Step1：创建一个 QueryWrapper 对象
        QueryWrapper queryWrapper = new QueryWrapper<Finance>();
        // Step2： 构造查询条件
        if (EmptyChecker.isEmpty(time)) {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(NameCode), "financeName", NameCode)
                    .eq(EmptyChecker.notEmpty(financeType), "financeType", financeType)
                    .ge(EmptyChecker.isEmpty(time), "financeTime", time[0])
                    .le(EmptyChecker.isEmpty(time), "financeTime", time[1])
                    .orderByDesc("financeTime");
        } else {
            queryWrapper
                    .eq(EmptyChecker.notEmpty(NameCode), "financeName", NameCode)
                    .eq(EmptyChecker.notEmpty(financeType), "financeType", financeType)
                    .orderByDesc("financeTime");
        }
        //创建Page对象
        //  current是在第几页
        //  limit是一页有几个
        Page<Finance> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        financeMapper.selectPage(eduTeacherPage, queryWrapper);

        //通过Page对象获取分页信息
        List<Finance> records = eduTeacherPage.getRecords(); //每页的数据 list集合
        long total = eduTeacherPage.getTotal(); //总记录数
        long pages = eduTeacherPage.getPages(); //总页数
        Map<String, Object> map = new HashMap<>();
        List<Map> UserMap = new ArrayList<>();
        List<Map> inComeMap = new ArrayList<>();
        for (Finance record : records) {
            // 获取人员
            Map<String, String> UserTemp = new HashMap<>();
            if (EmptyChecker.notEmpty(record.getFinanceUserId())) {
                UserTemp.put("UserTemp", userService.getUserNameById(record.getFinanceUserId().toString()));
                UserMap.add(UserTemp);
            }
            // 获取详细
            Map<String, String> inComeTemp = new HashMap<>();
            if (EmptyChecker.notEmpty(record.getIncomeId())) {
                inComeTemp.put("inComeTemp", incomeService.getIncome(record.getIncomeId()).getIncomeName());
                inComeMap.add(inComeTemp);
            } else {
                // 加空的占位
                inComeTemp.put("inComeTemp", "");
                inComeMap.add(inComeTemp);
            }
        }
        map.put("data", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("UserMap", UserMap);
        map.put("inComeMap", inComeMap);
        if (total == 0) {
            return new ResponseResult(202, "暂无数据");
        }
        return new ResponseResult(200, "查询成功", map);
    }


}
