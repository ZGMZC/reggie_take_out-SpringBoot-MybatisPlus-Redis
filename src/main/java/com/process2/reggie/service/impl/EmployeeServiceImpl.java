package com.process2.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.process2.reggie.entity.Employee;
import com.process2.reggie.mapper.EmployeeMapper;
import com.process2.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
