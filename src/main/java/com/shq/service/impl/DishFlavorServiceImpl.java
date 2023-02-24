package com.shq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.entity.DishFlavor;
import com.shq.mapper.DishFlavorMapper;
import com.shq.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
