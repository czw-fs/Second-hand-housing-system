package com.example.service;

import com.example.entify.Admin;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/28 17:27
 * @Description: everything is ok
 */
public interface AdminService extends BaseService<Admin>{
    List<Admin> findAll();


    Admin getAdminByUserName(String username);
}
