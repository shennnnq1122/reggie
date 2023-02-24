package com.shq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shq.dto.SetmealDto;
import com.shq.entity.Setmeal;
import org.springframework.stereotype.Service;


public interface SetmealService extends IService<Setmeal> {

    public void saveWithDishes(SetmealDto setmealDto);

    public SetmealDto getByIdWithDishes(Long id);

    public void updateWithDishes(SetmealDto setmealDto);

}
