package com.shq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.entity.Employee;
import com.shq.mapper.EmployeeMapper;
import com.shq.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

}
