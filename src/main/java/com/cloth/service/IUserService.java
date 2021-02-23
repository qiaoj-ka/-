package com.cloth.service;

import com.cloth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloth.entity.UserList;
import com.cloth.pojo.PasswordVO;
import com.cloth.pojo.UserExecution;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface IUserService extends IService<User> {

    User login(String account, String password);

    List<UserList>  findUserListByCondition(String name, Integer id);

    UserList findUserInfoByCondition(String name, Integer userId, Integer id);

    UserExecution addUserListAndUserAccount(UserList userList);

    UserExecution updateUserList(UserList userList);

    UserExecution deleteUser(Integer userListid);

    UserExecution updatePassword(PasswordVO passwordVO);
}
