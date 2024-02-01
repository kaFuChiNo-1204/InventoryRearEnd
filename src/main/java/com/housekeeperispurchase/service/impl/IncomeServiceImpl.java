package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.housekeeperispurchase.pojo.Income;
import com.housekeeperispurchase.mapper.IncomeMapper;
import com.housekeeperispurchase.service.IIncomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income> implements IIncomeService {

    @Autowired
    private IncomeMapper incomeMapper;


    public Income getIncome(int id){
        Income incomeId = incomeMapper.selectOne(new QueryWrapper<Income>().eq("incomeId", id));
        return incomeId;
    }

}
