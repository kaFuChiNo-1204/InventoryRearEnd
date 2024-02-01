package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.housekeeperispurchase.mapper.UserMapper;
import com.housekeeperispurchase.pojo.Additive;
import com.housekeeperispurchase.mapper.AdditiveMapper;
import com.housekeeperispurchase.pojo.User;
import com.housekeeperispurchase.service.IAdditiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-10-24
 */
@Service
public class AdditiveServiceImpl extends ServiceImpl<AdditiveMapper, Additive> implements IAdditiveService {

    @Autowired
    AdditiveMapper additiveMapper;

    //  添加附加成本的方法
    public ResponseResult addAdditive(Additive[] additive) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < additive.length; i++) {
            int insert = additiveMapper.insert(additive[i]);
            if (stringBuilder.length() > 0) {
                stringBuilder.append(","); // 添加逗号分隔符，确保不在第一个元素前添加逗号
            }
            if (insert > 0) {
                stringBuilder.append(additive[i].getAdditiveId());
            }
        }
        return new ResponseResult(200, "添加成功", stringBuilder);
    }

    // 计算附加成本总价值的方法
    public int getAdditivePriceTotal(String ids) {
        if (!EmptyChecker.notEmpty(ids)) return 0;
        String[] split = ids.split(",");
        Integer temp = 0;
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                Additive additive = additiveMapper.selectOne(new QueryWrapper<Additive>().eq("additiveId", split[i]));
                temp += additive.getAdditivePrice();
            }
        }
        return temp;
    }


    //    更加id查询附加成本
    public List<Additive> AdditiveById(String id) {
        String[] split = id.split(",");
        List list = new ArrayList();
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                Additive additive = additiveMapper.selectOne(new QueryWrapper<Additive>().eq("additiveId", split[i]));
                list.add(additive);
            }
        }
        return list;
    }

    //   修改附加成本
    public ResponseResult updateAdditive(Additive additive) {
        int i = additiveMapper.updateById(additive);
        if (i > 0) return new ResponseResult(200, "修改成功");
        return new ResponseResult(200, "修改失败");
    }

    //   删除附加成本
    public ResponseResult deleteAdditiveById(int id) {
        int i = additiveMapper.deleteById(id);
        if (i > 0) {
        //  同时把库存里面的附加成本id也删除
            return new ResponseResult(200, "删除成功");

        };
        return new ResponseResult(200, "删除失败");
    }
}
