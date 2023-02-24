package com.shq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shq.common.R;
import com.shq.dto.DishDto;
import com.shq.entity.Category;
import com.shq.entity.Dish;
import com.shq.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){

        log.info("page:{},pageSize:{},name",page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);

        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable int status,Long [] ids){

        log.info("updateStatus ,status{},ids{}",status,ids);

        ArrayList<Dish> dishArrayList =new ArrayList<>();
        for(Long id:ids){
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            dishArrayList.add(dish);
        }

        dishService.saveOrUpdateBatch(dishArrayList);

        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long [] ids){
        log.info("delete ,ids{}",ids);

        dishService.removeByIds(Arrays.asList(ids));

        return R.success("删除成功");
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        dishService.saveWithFlavor(dishDto);

        return R.success("菜品保存成功！");
    }

    @GetMapping("/{id}")
    public R<DishDto> getOne(@PathVariable Long id){
        log.info("getId:{}",id);

        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);

        return R.success(byIdWithFlavor);
    }

    @PutMapping
    private R<String> update(@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);

        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort);

        List<Dish> list = dishService.list(queryWrapper);

        return R.success(list);

    }

}
