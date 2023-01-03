package com.example.service;

import com.example.entify.UserInfo;

/**
 * @author: fs
 * @date: 2023/1/1 21:31
 * @Description: everything is ok
 */
public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);


}
