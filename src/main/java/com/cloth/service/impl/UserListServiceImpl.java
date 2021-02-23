package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloth.service.IUserListService;
import com.cloth.entity.UserList;
import com.cloth.mapper.UserListMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@Service
public class UserListServiceImpl extends ServiceImpl<UserListMapper, UserList> implements IUserListService {

    @Resource
    private UserListMapper userListMapper;

    @Override
    public UserList selectUser(String username, Integer userId, Integer id) {
        QueryWrapper<UserList> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("name",username);
        return userListMapper.selectOne(queryWrapper);
    }
}
