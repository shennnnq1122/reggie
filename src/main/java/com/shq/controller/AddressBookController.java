package com.shq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shq.common.BaseContext;
import com.shq.common.R;
import com.shq.entity.AddressBook;
import com.shq.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        Long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AddressBook::getUserId,userId);

        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }

    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook){

        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);

        addressBookService.save(addressBook);

        return R.success("修改成功");
    }

    @PutMapping("/default")
    public R<String> setAefault(@RequestBody AddressBook addressBook){

        LambdaUpdateWrapper<AddressBook> updateWrapper =  new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        updateWrapper.set(AddressBook::getIsDefault,0);

        addressBookService.update(updateWrapper);

        addressBook.setIsDefault(1);

        addressBookService.updateById(addressBook);

        return R.success("修改默认地址成功");

    }

    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1);
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());

        AddressBook one = addressBookService.getOne(queryWrapper);

        return R.success(one);
    }

    @GetMapping("/{id}")
    public R<AddressBook> getOne(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);

        return R.success(addressBook);
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改信息成功");
    }

    @DeleteMapping
    public R<String> delete(Long ids){

        addressBookService.removeById(ids);

        return R.success("删除地址信息成功");
    }



}
