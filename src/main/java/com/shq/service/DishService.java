package com.shq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shq.dto.DishDto;
import com.shq.entity.Dish;


public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

}
