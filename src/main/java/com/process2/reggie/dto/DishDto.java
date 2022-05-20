package com.process2.reggie.dto;

import com.process2.reggie.entity.Dish;
import com.process2.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品口味和菜品  关联类
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
