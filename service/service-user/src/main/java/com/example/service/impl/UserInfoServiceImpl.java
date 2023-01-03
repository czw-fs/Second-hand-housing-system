package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.UserInfoDao;
import com.example.entify.UserInfo;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: fs
 * @date: 2023/1/1 21:31
 * @Description: everything is ok
 */
@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public BaseDao<UserInfo> getEntityDao() {
        return this.userInfoDao;
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoDao.getUserInfoByPhone(phone);
    }


}
