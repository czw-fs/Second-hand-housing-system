package com.example.service;

import com.example.entify.Role;

import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/27 15:38
 * @Description: everything is ok
 */
public interface RoleService extends BaseService<Role>{
    //查所有角色
    List<Role> findAll();

    Map<String,Object> findRolesByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
