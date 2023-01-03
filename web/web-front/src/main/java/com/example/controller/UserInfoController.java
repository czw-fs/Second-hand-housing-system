package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.UserInfo;
import com.example.result.Result;
import com.example.result.ResultCodeEnum;
import com.example.service.UserInfoService;
import com.example.util.MD5;
import com.example.vo.LoginVo;
import com.example.vo.RegisterVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/1 21:36
 * @Description: everything is ok
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController extends BaseController{
    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone")String phone, HttpServletRequest request){
        //设置验证码
        String code = "8888";

        request.getSession().setAttribute("code",code);

        return Result.ok(code);
    }

    //注册
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession httpSession){
        //获取手机号,密码昵称和验证码
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        //验空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //从session域中获取验证码并验证
        String sessionCode =(String) httpSession.getAttribute("code");
        if(!code.equals(sessionCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }

        //判断该手机号是否已注册
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if(null != userInfo){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhone(phone);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setNickName(nickName);
        userInfo1.setStatus(1);

        userInfoService.insert(userInfo1);
        return Result.ok();
    }

    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpSession session){
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(password)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if(userInfo == null){
            //账号不正确
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }

        if(!MD5.encrypt(password).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        //判断用户是否被锁定
        if(userInfo.getStatus().equals(0)){
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //登录成功
        //将用户信息放到session域中
        session.setAttribute("user",userInfo);
        Map map = new HashMap();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",phone);

        return Result.ok(map);
    }

    //登出
    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("user");
        return Result.ok();
    }
}
