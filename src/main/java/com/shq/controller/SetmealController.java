package com.shq.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shq.common.R;
import com.shq.dto.DishDto;
import com.shq.dto.SetmealDto;
import com.shq.entity.Dish;
import com.shq.entity.Orders;
import com.shq.entity.Setmeal;
import com.shq.service.OrdersService;
import com.shq.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        log.info("page:{},pageSize:{},name{}",page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByAsc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
        
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable int status, Long [] ids){

        log.info("updateStatus ,status{},ids{}",status,ids);

        ArrayList<Setmeal> setmealArrayList =new ArrayList<>();

        for(Long id:ids){
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            setmealArrayList.add(setmeal);
        }

        setmealService.saveOrUpdateBatch(setmealArrayList);

        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long [] ids){
        log.info("delete ,ids{}",ids);

        setmealService.removeByIds(Arrays.asList(ids));

        return R.success("删除成功");
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDishes(setmealDto);

        return R.success("套餐信息保存成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getOne(@PathVariable Long id){
        log.info("getId:{}",id);

        SetmealDto setmealDto = setmealService.getByIdWithDishes(id);

        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){

        setmealService.updateWithDishes(setmealDto);

        return R.success("修改套餐信息成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Long categoryId){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(categoryId!=null,Setmeal::getCategoryId,categoryId);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

    @GetMapping("/dish/{id}")
    public R<Setmeal> getById(@PathVariable Long id){
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }

}
