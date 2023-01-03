package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.DictDao;
import com.example.dao.HouseDao;
import com.example.entify.House;
import com.example.service.HouseService;
import com.example.vo.HouseQueryVo;
import com.example.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author: fs
 * @date: 2022/12/31 15:27
 * @Description: everything is ok
 */
@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public BaseDao<House> getEntityDao() {
        return this.houseDao;
    }

    //发布和取消发布

    @Override
    public void publish(Long houseId, Integer status) {
        House house = new House();
        house.setId(houseId);
        house.setStatus(status);

        houseDao.update(house);
    }

    //重写getbyid展示房源中户型,楼层,朝向等信息
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        //获取户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //获取楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //获取朝向
        String directionName = dictDao.getNameById(house.getDirectionId());
        //获取建筑结构
        String buildStructure = dictDao.getNameById(house.getBuildStructureId());
        //获取装修情况
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //获取房屋用途
        String houseUseName = dictDao.getNameById(house.getHouseUseId());

        //设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDirectionName(directionName);
        house.setBuildStructureName(buildStructure);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);

        return house;
    }

    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        PageHelper.startPage(pageNum,pageSize);
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);

        for (HouseVo houseVo : page) {
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            String directionName = dictDao.getNameById(houseVo.getDirectionId());

            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }

        return new PageInfo<>(page,5);
    }
}
