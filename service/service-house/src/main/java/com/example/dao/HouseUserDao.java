package com.example.dao;

import com.example.entify.HouseUser;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:30
 * @Description: everything is ok
 */
public interface HouseUserDao extends BaseDao<HouseUser> {
     //根据该房源的id查询该房源的房东
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
