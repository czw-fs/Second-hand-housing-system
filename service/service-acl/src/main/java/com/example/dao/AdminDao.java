package com.example.dao;

import com.example.entify.Admin;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/28 17:22
 * @Description: everything is ok
 */
public interface AdminDao extends BaseDao<Admin>{
    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
