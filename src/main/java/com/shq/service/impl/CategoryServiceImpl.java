package com.shq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.common.CustomException;
import com.shq.entity.Category;
import com.shq.entity.Dish;
import com.shq.entity.Setmeal;
import com.shq.mapper.CategoryMapper;
import com.shq.service.CategoryService;
import com.shq.service.DishService;
import com.shq.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService ;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //判断当前分类是否关联了菜品
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);


        if(count1>0){
            //关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //判断当前分类是否关联了套餐
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);


        if(count2>0){
            //关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }

        //正常删除分类
        super.removeById(id);

    }
}
