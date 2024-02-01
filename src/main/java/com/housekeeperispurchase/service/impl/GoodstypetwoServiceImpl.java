package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.GoodstypethreeMapper;
import com.housekeeperispurchase.pojo.Goodstypeone;
import com.housekeeperispurchase.pojo.Goodstypethree;
import com.housekeeperispurchase.pojo.Goodstypetwo;
import com.housekeeperispurchase.mapper.GoodstypetwoMapper;
import com.housekeeperispurchase.service.IGoodstypetwoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.EmptyChecker;
import com.housekeeperispurchase.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class GoodstypetwoServiceImpl extends ServiceImpl<GoodstypetwoMapper, Goodstypetwo> implements IGoodstypetwoService {

    @Autowired
    GoodstypetwoMapper goodstypetwoMapper;


    public ResponseResult getTwo(int id) {
        return new ResponseResult(200, "查询成功", goodstypetwoMapper.selectList(new QueryWrapper<Goodstypetwo>().eq(EmptyChecker.notEmpty(id), "goodsTypeOneId", id)));
    }

    public ResponseResult getTwoById(int id, Long current, Long limit) {

        Page<Goodstypetwo> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        goodstypetwoMapper.selectPage(eduTeacherPage, new QueryWrapper<Goodstypetwo>().eq("goodsTypeOneId", id));

        //通过Page对象获取分页信息
        List<Goodstypetwo> records = eduTeacherPage.getRecords(); //每页的数据 list集合
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

    public ResponseResult addTwo(Goodstypetwo goodstypetwo) {
        List<Goodstypetwo> goodstypetwoList = goodstypetwoMapper.selectList(null);
        for (Goodstypetwo goodstypetwo1 : goodstypetwoList) {
            if (goodstypetwo1.getGoodsTypeTwoName().equals(goodstypetwo.getGoodsTypeTwoName())) {
                return new ResponseResult(202, "添加失败,名字重复");
            }
        }
        int insert = goodstypetwoMapper.insert(goodstypetwo);
        if (insert > 0) {
            return new ResponseResult(200, "添加成功");
        }
        return new ResponseResult(200, "添加失败");
    }

    //   修改二级类别
    public ResponseResult updateTwo(Goodstypetwo goodstypetwo) {
        int i = goodstypetwoMapper.updateById(goodstypetwo);
        if (i > 0) return new ResponseResult(200, "修改成功");
        return new ResponseResult(202, "修改失败");
    }

    //   根据id删除二级类别
    public ResponseResult deleTwoById(int id) {
        int i = goodstypetwoMapper.deleteById(id);
        if (i > 0) return new ResponseResult(200, "删除成功");
        return new ResponseResult(202, "删除失败");
    }

    //    根据二级id获取到二级类别
    public ResponseResult getTwoOne(int id) {
        return new ResponseResult(200, "查询成功", goodstypetwoMapper.selectOne(new QueryWrapper<Goodstypetwo>().eq("goodsTypeTwoId", id)));
    }
}
