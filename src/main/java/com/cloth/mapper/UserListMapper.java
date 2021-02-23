package com.cloth.mapper;

import com.cloth.entity.UserList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface UserListMapper extends BaseMapper<UserList> {

    List<UserList> selectUserListByCondition(String name, Integer id);


    UserList selectUserInfoByCondition(String name, Integer userId, Integer id);
}
