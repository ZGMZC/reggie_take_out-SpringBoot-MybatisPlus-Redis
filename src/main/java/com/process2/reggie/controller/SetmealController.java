package com.process2.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.process2.reggie.common.R;
import com.process2.reggie.dto.SetmealDto;
import com.process2.reggie.entity.Category;
import com.process2.reggie.entity.Setmeal;
import com.process2.reggie.service.CategoryService;
import com.process2.reggie.service.SetmealDishService;
import com.process2.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    /*    @CacheEvict是用来标注在需要清除缓存元素的方法或类上的。
    当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
    @CacheEvict可以指定的属性有value、key、condition、allEntries和beforeInvocation。
    其中value、key和condition的语义与@Cacheable对应的属性类似。
    即value表示清除操作是发生在哪些Cache上的（对应Cache的名称）；
    key表示需要清除的是哪个key，如未指定则会使用默认策略生成的key；
    condition表示清除操作发生的条件。
     allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。
     当指定了allEntries为true时，Spring Cache将忽略指定的key。
     */
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<>();


        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrappe=new LambdaQueryWrapper<>();
        //添加查询条件
        queryWrappe.like(name!=null, Setmeal::getName,name);
        //添加排序条件
        queryWrappe.orderByDesc(Setmeal::getUpdateTime);

        //进行查询
        setmealService.page(pageInfo,queryWrappe);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");;
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list=records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String name1 = category.getName();
                setmealDto.setCategoryName(name1);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
//        log.info("ids：{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }
    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("list")
    @Cacheable(value = "setmealCache",key ="#setmeal.categoryId+'_'+#setmeal.status" )
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
