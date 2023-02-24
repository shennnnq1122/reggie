package com.shq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.dto.SetmealDto;
import com.shq.entity.Setmeal;
import com.shq.entity.SetmealDish;
import com.shq.mapper.SetmealMapper;
import com.shq.service.DishService;
import com.shq.service.SetmealDishService;
import com.shq.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDishes(SetmealDto setmealDto) {

        this.save(setmealDto);

        setmealDto.getSetmealDishes().stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDto.getSetmealDishes());

    }

    @Transactional
    public SetmealDto getByIdWithDishes(Long id) {

        SetmealDto setmealDto = new SetmealDto();

        Setmeal setmeal = this.getById(id);

        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(id!=null,SetmealDish::getSetmealId,id);
        queryWrapper.orderByAsc(SetmealDish::getSort);

        setmealDto.setSetmealDishes(setmealDishService.list(queryWrapper));

        return setmealDto;
    }

    @Override
    public void updateWithDishes(SetmealDto setmealDto) {

        this.updateById(setmealDto);

        setmealDto.getSetmealDishes().stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.updateBatchById(setmealDto.getSetmealDishes());
    }
}
