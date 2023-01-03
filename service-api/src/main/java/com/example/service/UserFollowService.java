package com.example.service;

import com.example.entify.UserFollow;

/**
 * @author: fs
 * @date: 2023/1/2 12:49
 * @Description: everything is ok
 */
public interface UserFollowService extends BaseService<UserFollow> {
    //关注房源
    void follow(Long id, Long houseId);

    Boolean isFollowed(Long userId, Long houseId);
}
