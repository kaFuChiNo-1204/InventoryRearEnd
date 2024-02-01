package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.mapper.GoodsMapper;
import com.housekeeperispurchase.pojo.Goods;
import com.housekeeperispurchase.pojo.Goodstypeone;
import com.housekeeperispurchase.mapper.GoodstypeoneMapper;
import com.housekeeperispurchase.pojo.User;
import com.housekeeperispurchase.service.IGoodstypeoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.housekeeperispurchase.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
public class GoodstypeoneServiceImpl extends ServiceImpl<GoodstypeoneMapper, Goodstypeone> implements IGoodstypeoneService {


    @Autowired
    GoodstypeoneMapper goodstypeoneMapper;


    public ResponseResult getOne() {
        return new ResponseResult(200, "查询成功", goodstypeoneMapper.selectList(null));
    }

    public ResponseResult getOneById(Long current, Long limit) {

        Page<Goodstypeone> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        goodstypeoneMapper.selectPage(eduTeacherPage, null);

        //通过Page对象获取分页信息
        List<Goodstypeone> records = eduTeacherPage.getRecords(); //每页的数据 list集合
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

    public ResponseResult addOne(Goodstypeone goodstypeone) {
        List<Goodstypeone> goodstypeoneList = goodstypeoneMapper.selectList(null);
        for (Goodstypeone goodstypeone1 : goodstypeoneList) {
            if (goodstypeone1.getGoodsTypeOneName().equals(goodstypeone.getGoodsTypeOneName())) {
                return new ResponseResult(202, "添加失败,名字重复");
            }
        }
//        System.out.println("goodstypeone________________________________" + goodstypeone);
        int insert = goodstypeoneMapper.insert(goodstypeone);
        if (insert > 0) {
            return new ResponseResult(200, "添加成功");
        }
        return new ResponseResult(202, "添加失败");
    }

    //   修改一级类别
    public ResponseResult updateOne(Goodstypeone goodstypeone) {
//        System.out.println("goodstypeone_______________" + goodstypeone);
        int i = goodstypeoneMapper.updateById(goodstypeone);
        if (i > 0) return new ResponseResult(200, "修改成功");
        return new ResponseResult(202, "修改失败");
    }


    //   根据id删除一级类别
    public ResponseResult deleOneById(int id) {
        int i = goodstypeoneMapper.deleteById(id);
        if (i > 0) return new ResponseResult(200, "删除成功");
        return new ResponseResult(202, "删除失败");
    }


    //    根据一级id获取到一级类别
    public Goodstypeone getOneOne(int id) {
        return  goodstypeoneMapper.selectOne(new QueryWrapper<Goodstypeone>().eq("goodsTypeOneId", id));
    }
}
