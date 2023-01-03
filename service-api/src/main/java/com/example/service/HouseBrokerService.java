package com.example.service;

import com.example.entify.HouseBroker;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:40
 * @Description: everything is ok
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {

    //根据房源id查询该房源的经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
