package com.shq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shq.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);

}
