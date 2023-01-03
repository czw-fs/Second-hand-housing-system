package com.example.dao;

import com.example.entify.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: fs
 * @date: 2023/1/2 15:04
 * @Description: everything is ok
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleIdByAdminId(Long adminId);

    void deleteRoleIdByAdminId(Long adminId);

    void addNewRoleIdAndAdminId(@Param("roleId") Long roleId, @Param("adminId") Long adminId);
}
