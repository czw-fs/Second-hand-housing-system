package com.example.dao;

import com.example.entify.UserFollow;
import org.apache.ibatis.annotations.Param;

/**
 * @author: fs
 * @date: 2023/1/2 12:51
 * @Description: everything is ok
 */
public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);
}
