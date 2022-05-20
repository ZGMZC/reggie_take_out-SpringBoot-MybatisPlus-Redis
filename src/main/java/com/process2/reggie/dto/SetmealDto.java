package com.process2.reggie.dto;

import com.process2.reggie.entity.Setmeal;
import com.process2.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

/**
 * 套餐与菜品关联类
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
