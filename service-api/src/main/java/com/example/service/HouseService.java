package com.example.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.House;
import com.example.vo.HouseQueryVo;
import com.example.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author: fs
 * @date: 2022/12/31 15:26
 * @Description: everything is ok
 */
public interface HouseService extends BaseService<House> {
    ////发布和取消发布
    void publish(Long houseId, Integer status);

    //前端分页及带条件的查询方法
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);

}
