package com.process2.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.process2.reggie.common.CustomException;
import com.process2.reggie.entity.Category;
import com.process2.reggie.entity.Dish;
import com.process2.reggie.entity.Setmeal;
import com.process2.reggie.mapper.CategoryMapper;
import com.process2.reggie.service.CategoryService;
import com.process2.reggie.service.DishService;
import com.process2.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService  {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询 当前分类是否有菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }
        //查询当前分类是否有套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }
        //正常删除
        super.removeById(id);
    }
}
