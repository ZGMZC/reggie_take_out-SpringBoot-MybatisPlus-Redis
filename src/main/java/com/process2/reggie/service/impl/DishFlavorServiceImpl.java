package com.process2.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.process2.reggie.entity.DishFlavor;
import com.process2.reggie.mapper.DishFlavorMapper;
import com.process2.reggie.service.DishFlavorService;
import com.process2.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
