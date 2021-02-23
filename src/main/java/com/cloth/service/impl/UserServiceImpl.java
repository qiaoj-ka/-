package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloth.entity.UserList;
import com.cloth.exception.UserOperationException;
import com.cloth.mapper.UserListMapper;
import com.cloth.pojo.PasswordVO;
import com.cloth.pojo.UserExecution;
import com.cloth.service.IUserService;
import com.cloth.entity.User;
import com.cloth.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserListMapper userListMapper;
    @Override
    public User login(String account, String password) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",account).eq("password",password);
        User user=userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public List<UserList> findUserListByCondition(String name, Integer id) {
        QueryWrapper<UserList> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name).or().eq("user_id",id);
        return userListMapper.selectList(queryWrapper);
    }

    @Override
    public UserList findUserInfoByCondition(String name, Integer userId, Integer id) {
        QueryWrapper<UserList> queryWrapper=new QueryWrapper<>();
        //User user=userMapper.selectById(id);
        queryWrapper.eq("id",id);
        //return userListMapper.selectUserInfoByCondition(name,userId,id);
        return userListMapper.selectOne(queryWrapper);
    }

    @Override
    public UserExecution addUserListAndUserAccount(UserList userList) {
        if(userList == null){
            return new UserExecution(false,"添加用户信息为空");
        }
        User user = new User();
        user.setUsername(userList.getName());
        user.setPassword(userList.getPhone());
        user.setType(userList.getType());
        try{
            int effectedNum = userMapper.insert(user);
            if(effectedNum < 1){
                return new UserExecution(false,"添加用户帐号失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        int accountId = user.getId();
        userList.setUserId(accountId);
        try{
            int effectedNum = userListMapper.insert(userList);
            if(effectedNum < 1){
                return new UserExecution(false,"添加用户信息失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
    }

    @Override
    public UserExecution updateUserList(UserList userList) {
        if(userList == null){
            return new UserExecution(false,"更新用户信息为空");
        }

        try{
            int effectedNum = userListMapper.updateById(userList);
            if(effectedNum < 1){
                return new UserExecution(false,"更新用户信息失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
    }

    @Override
    public UserExecution deleteUser(Integer userListid) {
        if(userListid == null){
            return new UserExecution(false,"删除用户信息Id为空");
        }
        UserList userList = userListMapper.selectById(userListid);
        Integer userAccountId = userList.getUserId();
        try{
            int effectedNum =userMapper.deleteById(userAccountId);
            if(effectedNum < 1){
                return new UserExecution(false,"删除用户帐号失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }

        try{
            int effectedNum = userListMapper.deleteById(userListid);
            if(effectedNum < 1){
                return new UserExecution(false,"删除用户信息失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
    }

    @Override
    public UserExecution updatePassword(PasswordVO passwordVO) {
        if(passwordVO == null){
            return new UserExecution(false,"修改的密码信息为空");
        }
        UserList userList = userListMapper.selectById(passwordVO.getUserId());
        Integer userId = userList.getUserId();
        User user = userMapper.selectById(userId);
        if(!passwordVO.getOldPassword().equals(user.getPassword())){
            return new UserExecution(false,"旧密码错误");
        }
        user.setPassword(passwordVO.getNewPassword());
        try{
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId);
            int effectedNum = userMapper.update(user,new QueryWrapper<>());
            if(effectedNum < 1){
                return new UserExecution(false,"修改密码失败");
            }
        }catch (Exception e){
            throw new UserOperationException(e.toString());
        }
        return new UserExecution(true);
    }


}
