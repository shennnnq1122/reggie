package com.shq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.dto.DishDto;
import com.shq.entity.Dish;
import com.shq.entity.DishFlavor;
import com.shq.mapper.DishMapper;
import com.shq.service.DishFlavorService;
import com.shq.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;


    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存基本信息到菜品表
        this.save(dishDto);

        Long dishId = dishDto.getId();

        //保存信息到菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors() ;
        flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    @Transactional
    public DishDto getByIdWithFlavor(Long id) {

        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);

        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //保存基本信息到菜品表
        this.updateById(dishDto);

        Long dishId = dishDto.getId();

        //保存信息到菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors() ;
        flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.updateBatchById(flavors);

    }
}
