package com.housekeeperispurchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.housekeeperispurchase.pojo.User;
import com.housekeeperispurchase.mapper.UserMapper;
import com.housekeeperispurchase.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.housekeeperispurchase.utils.ResponseResult;
import com.housekeeperispurchase.utils.EmptyChecker;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 获取员工所有的名字和id
     *
     * @return
     */
    @Override
    public ResponseResult selectName() {
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        queryWrapper.select("userId", "userName");
        List list = userMapper.selectList(queryWrapper);
        return new ResponseResult(200, "查询成功", list);
    }

    /**
     * 根据名字和登记时间来查找业主的信息
     *
     * @param name    名字
     * @param current 页码
     * @param limit   页数量
     * @return
     */
    public ResponseResult selectListUserBy(String name, long current, Long limit) {
        // Step1：创建一个 QueryWrapper 对象
        QueryWrapper queryWrapper = new QueryWrapper<User>();

        // Step2： 构造查询条件
        queryWrapper
                .eq(EmptyChecker.notEmpty(name), "userName", name);
        //创建Page对象
        //  current是在第几页
        //  limit是一页有几个
        Page<User> eduTeacherPage = new Page<>(current, limit);

        //调用mybatis plus分页方法进行查询
        userMapper.selectPage(eduTeacherPage, queryWrapper);

        //通过Page对象获取分页信息
        List<User> records = eduTeacherPage.getRecords(); //每页的数据 list集合
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


    //    添加用户的方法
    public ResponseResult addUser(User user) {
        List<User> users = userMapper.selectList(null);
        for (User user1 : users) {
            if (user1.getUserName().equals(user.getUserName())) {
                return new ResponseResult(202, "添加失败,员工姓名重复");
            }
        }
        userMapper.insert(user);
        return new ResponseResult(200, "添加成功");
    }

    //    修改用户的方法
    public ResponseResult updateUser(User user) {
        List<User> users = userMapper.selectList(null);
        for (User user1 : users) {
            if ((user1.getUserName().equals(user.getUserName()))
                    && (user1.getUserId() != (user.getUserId()))) {
                return new ResponseResult(202, "修改失败,业主姓名重复");
            }
        }
        int i = userMapper.updateById(user);
        if (i > 0) {
            return new ResponseResult(200, "修改成功");
        }
        return new ResponseResult(202, "修改失败,未知原因");
    }

    //    删除用户
    public ResponseResult deleteUserById(int id) {
        if (userMapper.deleteById(id) > 0) {
            return new ResponseResult(200, "删除成功");
        }
        return new ResponseResult(202, "删除失败,未知错误");
    }

    //    根据id获取用户姓名
    public String getUserNameById(String id) {
        User userId = userMapper.selectOne(new QueryWrapper<User>().eq(EmptyChecker.notEmpty(id), "userId", id));
        if (EmptyChecker.notEmpty(userId) && !userId.getUserName().equals(null)) {
            return userId.getUserName();
        }
        return null;
    }

}
