package com.example.dao;

import com.example.entify.House;
import com.example.vo.HouseQueryVo;
import com.example.vo.HouseVo;
import com.github.pagehelper.Page;

/**
 * @author: fs
 * @date: 2022/12/31 15:30
 * @Description: everything is ok
 */
public interface HouseDao extends BaseDao<House> {
    //分页及带条件的查询方法
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
