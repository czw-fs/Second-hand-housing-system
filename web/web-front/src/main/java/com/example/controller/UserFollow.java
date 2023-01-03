package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.UserInfo;
import com.example.result.Result;
import com.example.service.UserFollowService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: fs
 * @date: 2023/1/2 12:48
 * @Description: everything is ok
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollow extends BaseController{
    @Reference
    private UserFollowService userFollowService;

    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId")Long houseId , HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }
}
