package com.example.dao;

import com.example.entify.Role;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/27 15:34
 * @Description: everything is ok
 */

public interface RoleDao extends BaseDao<Role> {

    //查询所有人
    List<Role> findAll();




}
