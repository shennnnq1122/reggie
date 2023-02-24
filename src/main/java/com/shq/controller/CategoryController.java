package com.shq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shq.common.R;
import com.shq.entity.Category;
import com.shq.entity.Employee;
import com.shq.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private  CategoryService categoryService ;


    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);

        categoryService.save(category);
        return R.success("添加分类信息成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        log.info("page:{},pageSize:{}",page,pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件过滤器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        //queryWrapper.orderByDesc();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("delete ids:{}",ids);

        categoryService.remove(ids);

        return R.success("删除成功");

    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("update category:{}",category);

        categoryService.updateById(category);

        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);

    }


}
