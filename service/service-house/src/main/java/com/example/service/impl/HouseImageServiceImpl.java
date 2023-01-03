package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.HouseImageDao;
import com.example.entify.HouseImage;
import com.example.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:48
 * @Description: everything is ok
 */

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    public BaseDao<HouseImage> getEntityDao() {
        return this.houseImageDao;
    }

    @Override
    public List<HouseImage> getHouseImagesByHouseIdAndType(Long houseId, Integer type) {
        return houseImageDao.getHouseImagesByHouseIdAndType(houseId,type);
    }
}
