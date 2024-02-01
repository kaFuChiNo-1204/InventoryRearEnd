package com.housekeeperispurchase.mapper;

import com.housekeeperispurchase.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<String> selectName();

}
