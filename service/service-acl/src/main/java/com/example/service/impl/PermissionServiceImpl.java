package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.PermissionDao;
import com.example.dao.RolePermissionDao;
import com.example.entify.Permission;
import com.example.helper.PermissionHelper;
import com.example.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/2 16:15
 * @Description: everything is ok
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public BaseDao<Permission> getEntityDao() {
        return this.permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionsByRoleId(Long roleId) {
        //获取所有的权限
        List<Permission> permissionList = permissionDao.findAll();
        //根据角色id查询已分配权限的id
        List<Long> permissionIds = rolePermissionDao.findPermissionIdsByRoleId(roleId);
        //创建返回的list
        List<Map<String,Object>> returnList = new ArrayList<>();
        //遍历所有的权限
        for (Permission permission : permissionList) {
            Map map = new HashMap();

            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());

            if (permissionIds.contains(permission.getId())){
                map.put("checked",true);
            }
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        //删除已分配的权限
        rolePermissionDao.deletePermissionIdsByRoleId(roleId);
        //遍历所有权限id,重新保存已分配的权限
        for (Long permissionId : permissionIds) {
            if(permissionId != null){
                rolePermissionDao.addRoleIdAndPermissionId(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getMenuPermissionByuserId(Long userId) {

        List<Permission> permissionList = null;

        //判断是否是系统管理员
        if(userId == 1){
            //获取所有权限
            permissionList = permissionDao.findAll();
        }else{
            permissionList = permissionDao.getMenuPermissionByAdminId(userId);
        }
        //通过PermissionHelper工具类将list转为树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);
        return treeList;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes = null;
        if(id == 1){
            //是系统管理员
            permissionCodes = permissionDao.getAllPermissionCodes();
        }else{
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }

        return permissionCodes;
    }
}
