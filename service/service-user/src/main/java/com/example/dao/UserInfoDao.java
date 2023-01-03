package com.example.dao;

import com.example.entify.UserInfo;

/**
 * @author: fs
 * @date: 2023/1/1 21:32
 * @Description: everything is ok
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
