package com.process2.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.process2.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
