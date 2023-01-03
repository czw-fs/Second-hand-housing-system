package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Admin;
import com.example.entify.Permission;
import com.example.service.AdminService;
import com.example.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/27 16:34
 * @Description: everything is ok
 */
@Controller
public class IndexController {



    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/")
    public String index(Map map){
        //根据用户id查询其权限菜单
/*        Long userId = 1L;
        Admin admin = adminService.getById(userId);*/

        //通过Spring Security获取User对象
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = user.getUsername();
        Admin admin = adminService.getAdminByUserName(username);

        List<Permission> permissionList = permissionService.getMenuPermissionByuserId(admin.getId());
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }

/*
    @RequestMapping("/")
    public String index(){
        return "frame/index";
    }
*/

    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    @RequestMapping("/login")
    public String goLoginPage(){
        return "frame/login";
    }

    //去没有权限的提示页面
    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }

}
