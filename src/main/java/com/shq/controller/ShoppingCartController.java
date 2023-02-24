package com.shq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shq.common.R;
import com.shq.entity.ShoppingCart;
import com.shq.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpServletRequest request){

        Long userId = (Long) request.getSession().getAttribute("userId");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId!=null,ShoppingCart::getUserId,userId);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart,HttpServletRequest request){

        Long userId = (Long) request.getSession().getAttribute("userId");
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(userId!=null,ShoppingCart::getUserId,userId);
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());

        int count = shoppingCartService.count(queryWrapper);
        if(count==0){
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
        }
        else {
            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            one.setNumber(one.getNumber()+1);
            shoppingCartService.updateById(one);
        }

        return R.success("添加成功");
    }

    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart,HttpServletRequest request){

        Long userId = (Long) request.getSession().getAttribute("userId");

        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(userId!=null,ShoppingCart::getUserId,userId);
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());

        ShoppingCart one = shoppingCartService.getOne(queryWrapper);


        if(one.getNumber()==1){
            shoppingCartService.removeById(one);
        }
        else{
            one.setNumber(one.getNumber()-1);
            shoppingCartService.updateById(one);
        }
        return R.success("取消成功");
    }

    @DeleteMapping("clean")
    public R<String> clean(HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute("userId");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(userId!=null,ShoppingCart::getUserId,userId);
        shoppingCartService.remove(queryWrapper);

        return R.success("清除成功");
    }

}
