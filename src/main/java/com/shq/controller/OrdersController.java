package com.shq.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shq.common.R;
import com.shq.entity.Orders;
import com.shq.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        log.info("page:{},pageSize:{}",page,pageSize);

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.orderByAsc(Orders::getOrderTime);

        ordersService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);

    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize, HttpServletRequest request){

        log.info("page:{},pageSize:{}",page,pageSize);

        Long userId = (Long) request.getSession().getAttribute("UserId");

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(userId!=null,Orders::getUserId,userId);
        queryWrapper.orderByAsc(Orders::getOrderTime);

        ordersService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);

    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }

}
