package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.mapper.AccountMapper;
import com.housekeeperispurchase.mapper.FinanceMapper;
import com.housekeeperispurchase.pojo.Account;
import com.housekeeperispurchase.pojo.Finance;
import com.housekeeperispurchase.service.IAccountService;
import com.housekeeperispurchase.service.IEchartsService;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import com.housekeeperispurchase.utils.TokenTools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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
public class EchartsServiceImpl implements IEchartsService {

    @Autowired
    FinanceMapper financeMapper;

    public ResponseResult getInit1Data(String[] time) {
        QueryWrapper<Finance> queryWrapper = new QueryWrapper();

        queryWrapper.select("financeBalance", "financeType").ge(EmptyChecker.isEmpty(time), "financeTime", time[0])
                .le(EmptyChecker.isEmpty(time), "financeTime", time[1]);
        List<Finance> list = financeMapper.selectList(queryWrapper);
        Double disburseSum = 0.0;// 支出的金额
        Double incomeSum = 0.0;// 收入的金额
        for (Finance finance : list) {
            if (finance.getFinanceType().equals("支出")) {
                disburseSum += Double.parseDouble(finance.getFinanceBalance().substring(1));
            }
            if (finance.getFinanceType().equals("收入")) {
                incomeSum +=Double.parseDouble(finance.getFinanceBalance().substring(1));
            }
        }
        Map<String, Double> map = new HashMap<>();
        map.put("disburseSum",disburseSum);
        map.put("incomeSum",incomeSum);

        return new ResponseResult(200, "查询成功", map);
    }

    public ResponseResult getInit2Data(String[] time) {
        QueryWrapper<Finance> queryWrapper = new QueryWrapper();

        queryWrapper.select("financeBalance", "financeType","financeTime").ge(EmptyChecker.isEmpty(time), "financeTime", time[0])
                .le(EmptyChecker.isEmpty(time), "financeTime", time[1]);
        List<Finance> list = financeMapper.selectList(queryWrapper);
        Double disburseSum = 0.0;// 支出的金额
        Double incomeSum = 0.0;// 收入的金额
        for (Finance finance : list) {
            if (finance.getFinanceType().equals("支出")) {
                disburseSum += Double.parseDouble(finance.getFinanceBalance().substring(1));
            }
            if (finance.getFinanceType().equals("收入")) {
                incomeSum +=Double.parseDouble(finance.getFinanceBalance().substring(1));
            }
        }
        Map<String, Double> map = new HashMap<>();
        map.put("disburseSum",disburseSum);
        map.put("incomeSum",incomeSum);

        return new ResponseResult(200, "查询成功", map);
    }

    public ResponseResult getInit3Data(String[] time) {
        return new ResponseResult(200, "查询成功", '3');
    }

    public ResponseResult getInit4Data(String[] time) {
        return new ResponseResult(200, "查询成功", '4');
    }

    public ResponseResult getInit5Data(String[] time) {
        return new ResponseResult(200, "查询成功", '5');
    }


}
