package com.example.config;


import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Admin;
import com.example.entify.Permission;
import com.example.service.AdminService;
import com.example.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fs
 * @date: 2023/1/3 13:55
 * @Description: everything is ok
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Reference
    private PermissionService permissionService;

    @Reference
    private AdminService adminService;
    //登录时,SpringSecurity会自动调用该方法,并将前端用户名传入到方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUserName(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户不存在!");
        }

        //获取当前用户权限的方法
        List<String> permissionCodes = permissionService.getPermissionCodesByAdminId(admin.getId());

        //创建一个用于授权的对象
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String permissionCode : permissionCodes) {

            if(!StringUtils.isEmpty(permissionCode)){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
                grantedAuthorities.add(simpleGrantedAuthority);
            }

        }
        //给用户授权
        //这里传入数据库中查出的密码会和前端传入的密码进行校验
        /*return new User(username,admin.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
        return new User(username,admin.getPassword(),grantedAuthorities);
    }
}
