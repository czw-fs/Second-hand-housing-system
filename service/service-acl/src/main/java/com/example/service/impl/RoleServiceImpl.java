package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.AdminRoleDao;
import com.example.dao.BaseDao;
import com.example.dao.RoleDao;
import com.example.entify.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/27 15:39
 * @Description: everything is ok
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        List<Role> roleList = roleDao.findAll();
        //获取用户已拥有角色的角色id
        List<Long> roleIds = adminRoleDao.findRoleIdByAdminId(adminId);
        //创建两个list,一个存放未选中的角色,一个存放选中的角色
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : roleList) {
            //判断当前角色的id在不在集合roleIds中
            if(roleIds.contains(role.getId())){
                assginRoleList.add(role);
            }else {
                noAssginRoleList.add(role);
            }
        }
        //创建返回的map
        Map<String,Object>  roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList",noAssginRoleList);
        roleMap.put("assginRoleList",assginRoleList);
        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //先根据用户id将已分配的角色删除
        adminRoleDao.deleteRoleIdByAdminId(adminId);
        //将新分配的角色id和用户id插入到数据库中
        for (Long roleId : roleIds) {
            if(roleId != null){
                adminRoleDao.addNewRoleIdAndAdminId(roleId,adminId);
            }
        }
    }
}
