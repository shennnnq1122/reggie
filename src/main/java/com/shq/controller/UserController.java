package com.shq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shq.common.BaseContext;
import com.shq.common.R;
import com.shq.entity.User;
import com.shq.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpServletRequest request){

        log.info("手机端登录号码：{}",user.getPhone());

        //查询手机号是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(user.getPhone()!=null,User::getPhone,user.getPhone());
        int num = userService.count(queryWrapper);

        if(num==0){//如果不存在创建新用户
            user.setName(user.getPhone()+"用户");
            user.setSex("0");
            user.setStatus(1);
            userService.save(user);
        }
        User one = userService.getOne(queryWrapper);

        request.getSession().setAttribute("userId",one.getId());

        return R.success("登陆成功");
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的id
        request.getSession().removeAttribute("userId");
        return R.success("退出成功");
    }


}
