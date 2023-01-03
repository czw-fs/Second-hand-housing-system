package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.HouseBrokerDao;
import com.example.entify.HouseBroker;
import com.example.service.HouseBrokerService;
import com.example.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:45
 * @Description: everything is ok
 */
@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerDao houseBrokerDao;
    @Override
    public BaseDao<HouseBroker> getEntityDao() {
        return this.houseBrokerDao;
    }

    @Override
    public List<HouseBroker> getHouseBrokerByHouseId(Long houseId) {
        return houseBrokerDao.getHouseBrokerByHouseId(houseId);
    }
}
