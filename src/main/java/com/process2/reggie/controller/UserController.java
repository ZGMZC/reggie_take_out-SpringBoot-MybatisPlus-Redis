package com.process2.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.process2.reggie.common.R;
import com.process2.reggie.common.ValidateCodeUtils;
import com.process2.reggie.entity.User;
import com.process2.reggie.service.UserService;
import com.process2.reggie.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机的验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云短信服务的api
//            SMSUtils.sendMessage("test1", ,phone,code);
            //保存生成的验证码
            session.setAttribute(phone,code);
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");
    }
    @PostMapping("login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        log.info("phone={}",phone);
        //获取验证码
        String code = map.get("code").toString();
        log.info("code={}",code);
        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对
        if(codeInSession!=null&&codeInSession.equals(code)){
            //比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            //判断是否为新用户， 是新用户则完成注册
            if(user==null){
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }
}
