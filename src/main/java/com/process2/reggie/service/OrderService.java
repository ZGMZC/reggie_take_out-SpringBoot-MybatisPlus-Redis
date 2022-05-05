package com.process2.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.process2.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
