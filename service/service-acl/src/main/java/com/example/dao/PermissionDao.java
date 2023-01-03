package com.example.dao;

import com.example.entify.Permission;

import java.util.List;

/**
 * @author: fs
 * @date: 2023/1/2 16:16
 * @Description: everything is ok
 */
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> getMenuPermissionByAdminId(Long adminId);

    List<String> getAllPermissionCodes();

    List<String> getPermissionCodesByAdminId(Long id);
}
