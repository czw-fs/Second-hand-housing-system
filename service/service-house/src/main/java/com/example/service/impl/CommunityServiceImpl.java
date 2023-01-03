package com.example.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.BaseDao;
import com.example.dao.CommunityDao;
import com.example.dao.DictDao;
import com.example.entify.Community;
import com.example.service.CommunityService;
import com.example.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/31 10:48
 * @Description: everything is ok
 */
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public BaseDao getEntityDao() {
        return this.communityDao;
    }

    //重写分页方法,目的是给校区中的区域和板块的名字赋值
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        List<Community> page = communityDao.findPage(filters);

        for(Community community : page){
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }

        return new PageInfo<>(page,10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        String areaName = dictDao.getNameById(community.getAreaId());
        String plateName = dictDao.getNameById(community.getPlateId());
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }
}
