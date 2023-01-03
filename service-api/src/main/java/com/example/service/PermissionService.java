package com.example.service;

import com.example.entify.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/2 16:14
 * @Description: everything is ok
 */
public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> findPermissionsByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByuserId(Long userId);

    List<String> getPermissionCodesByAdminId(Long id);
}
