package com.example.service;

import com.example.entify.HouseUser;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:42
 * @Description: everything is ok
 */
public interface HouseUserService extends BaseService<HouseUser> {
    //根据该房源的id查询该房源的房东
    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
