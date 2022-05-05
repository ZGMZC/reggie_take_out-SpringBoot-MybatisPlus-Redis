package com.process2.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.process2.reggie.dto.DishDto;
import com.process2.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，操作两张表 dish dishflavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品信息和口味信息
    void updateWithFlavor(DishDto dishDto);
}
