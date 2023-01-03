package com.example.dao;

import com.example.dao.BaseDao;
import com.example.entify.HouseBroker;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:13
 * @Description: everything is ok
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    //根据房源id查询该房源的经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
