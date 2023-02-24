package com.shq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shq.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id) ;
}
