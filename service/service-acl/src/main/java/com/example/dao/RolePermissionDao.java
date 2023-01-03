package com.example.dao;

import com.example.entify.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: fs
 * @date: 2023/1/2 16:20
 * @Description: everything is ok
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdsByRoleId(Long roleId);

    void deletePermissionIdsByRoleId(Long roleId);

    void addRoleIdAndPermissionId(@Param("roleId")Long roleId, @Param("permissionId") Long permissionId);
}
