package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.UserFollowDao;
import com.example.entify.UserFollow;
import com.example.entify.UserInfo;
import com.example.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: fs
 * @date: 2023/1/2 12:50
 * @Description: everything is ok
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class userFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Override
    public BaseDao<UserFollow> getEntityDao() {
        return this.userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setHouseId(houseId);
        userFollow.setUserId(id);

        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        //查询是否关注该房源
        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId,houseId);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
}
