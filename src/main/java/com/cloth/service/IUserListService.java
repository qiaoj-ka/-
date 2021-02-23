package com.cloth.service;

import com.cloth.entity.UserList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface IUserListService extends IService<UserList> {

    UserList selectUser(String username, Integer userId, Integer id);
}
