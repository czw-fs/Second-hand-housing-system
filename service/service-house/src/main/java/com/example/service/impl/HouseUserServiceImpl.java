package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.HouseUserDao;
import com.example.entify.HouseUser;
import com.example.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:51
 * @Description: everything is ok
 */
@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    public BaseDao<HouseUser> getEntityDao() {
        return this.houseUserDao;
    }

    @Override
    public List<HouseUser> getHouseUserByHouseId(Long houseId) {
        return houseUserDao.getHouseUserByHouseId(houseId);
    }
}
