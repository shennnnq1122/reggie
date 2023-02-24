package com.shq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.entity.OrderDetail;
import com.shq.mapper.OrderDetailMapper;
import com.shq.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
