package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.GoodstypeoneMapper;
import com.housekeeperispurchase.mapper.GoodstypetwoMapper;
import com.housekeeperispurchase.pojo.Goodstypeone;
import com.housekeeperispurchase.pojo.Goodstypethree;
import com.housekeeperispurchase.mapper.GoodstypethreeMapper;
import com.housekeeperispurchase.pojo.Goodstypetwo;
import com.housekeeperispurchase.service.IGoodstypethreeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class GoodstypethreeServiceImpl extends ServiceImpl<GoodstypethreeMapper, Goodstypethree> implements IGoodstypethreeService {


    @Autowired
    GoodstypethreeMapper goodstypethreeMapper;


    public ResponseResult getThree(int id) {
        return new ResponseResult(200, "查询成功", goodstypethreeMapper.selectList(new QueryWrapper<Goodstypethree>().eq(EmptyChecker.notEmpty(id), "goodsTypeTwoId", id)));
    }

    public ResponseResult getThreeById(int id, Long current, Long limit) {

        Page<Goodstypethree> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        goodstypethreeMapper.selectPage(eduTeacherPage, new QueryWrapper<Goodstypethree>().eq("goodsTypeTwoId", id));

        //通过Page对象获取分页信息
        List<Goodstypethree> records = eduTeacherPage.getRecords(); //每页的数据 list集合
        long total = eduTeacherPage.getTotal(); //总记录数
        long pages = eduTeacherPage.getPages(); //总页数

        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("total", total);
        map.put("pages", pages);

        if (total == 0) {
            return new ResponseResult(202, "暂无数据");
        }
        return new ResponseResult(200, "查询成功", map);
    }


    public ResponseResult addThree(Goodstypethree goodstypethree) {
        List<Goodstypethree> goodstypethreeList = goodstypethreeMapper.selectList(null);
        for (Goodstypethree goodstypetwo : goodstypethreeList) {
            if (goodstypetwo.getGoodsTypeThreeName().equals(goodstypethree.getGoodsTypeThreeName())) {
                return new ResponseResult(202, "添加失败,名字重复");
            }
        }
        int insert = goodstypethreeMapper.insert(goodstypethree);
        if (insert > 0) {
            return new ResponseResult(200, "添加成功");
        }
        return new ResponseResult(200, "添加失败");
    }

    //   修改三级类别
    public ResponseResult updateThree(Goodstypethree goodstypethree) {
        int i = goodstypethreeMapper.updateById(goodstypethree);
        if (i > 0) return new ResponseResult(200, "修改成功");
        return new ResponseResult(202, "修改失败");
    }

    //   根据id删除三级类别
    public ResponseResult deleThreeById(int id) {
        int i = goodstypethreeMapper.deleteById(id);
        if (i > 0) return new ResponseResult(200, "删除成功");
        return new ResponseResult(202, "删除失败");
    }


    //    根据三级id获取到三级类别
    public ResponseResult getThreeOne(int id) {
        Goodstypethree goodsTypeThreeId = goodstypethreeMapper.selectOne(new QueryWrapper<Goodstypethree>().eq(EmptyChecker.notEmpty(id), "goodsTypeThreeId", id));
        return new ResponseResult(200, "查询成功", goodsTypeThreeId);
    }
}
