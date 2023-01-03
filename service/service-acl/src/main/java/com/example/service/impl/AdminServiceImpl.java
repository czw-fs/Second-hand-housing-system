package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.AdminDao;
import com.example.dao.BaseDao;
import com.example.entify.Admin;
import com.example.service.AdminService;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/28 17:28
 * @Description: everything is ok
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Override
    public BaseDao<Admin> getEntityDao() {
        return this.adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminDao.getAdminByUserName(username);
    }
}
