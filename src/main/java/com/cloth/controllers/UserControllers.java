package com.cloth.controllers;

import com.cloth.pojo.LoginUser;
import com.cloth.pojo.User;
import com.cloth.pojo.UserList;
import com.cloth.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserControllers {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody LoginUser loginUser) {
        Map<String,Object> map = new HashMap<>();
        //User user = userService.login(loginUser.getAccount(),loginUser.getPassword());
        User user=new User(123,"乔","123",0);
        if(user == null){
            map.put("flag",false);
            return map;
        }
        UserList userList = new UserList(1,"乔","123","15336155900",1,1);
        //生成令牌
        JwtUtil jwtUtil = new JwtUtil();
        String token = null;
        if(userList.getType() == 1){
            map.put("systemRole","admin");
            token = jwtUtil.createJWT(String.valueOf(userList.getId()),user.getUsername(),"admin");

        }else {
            map.put("systemRole","user");
            token = jwtUtil.createJWT(String.valueOf(userList.getId()),user.getUsername(),"user");
        }

        map.put("userInfo",userList);
        map.put("token",token);
        map.put("flag",true);
        return map;
    }
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
}
