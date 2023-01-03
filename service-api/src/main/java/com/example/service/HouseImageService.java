package com.example.service;

import com.example.entify.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 21:41
 * @Description: everything is ok
 */
public interface HouseImageService extends BaseService<HouseImage> {
    //根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(@Param("houseId")Long houseId, @Param("type")Integer type);
}
