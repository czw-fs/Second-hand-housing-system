package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.*;
import com.example.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/31 15:21
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    @RequestMapping
    public String index(Map map , HttpServletRequest request){
        Map<String,Object> filters = getFilters(request);
        map.put("filters",filters);

        PageInfo<House> page = houseService.findPage(filters);
        map.put("page",page);

        setRequestAttribute(map);

        return "house/index";
    }

    //去添加房源的页面
    @RequestMapping("/create")
    public String goAddPage(Map map){
        setRequestAttribute(map);

        return "house/create";
    }
    //添加
    @RequestMapping("/save")
    public String insert(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    //去修改页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id")Long id, Map map){

        House house = houseService.getById(id);
        map.put("house",house);

        setRequestAttribute(map);

        return "house/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    //将小区及所有数据字典的方法放到request域中
    public void setRequestAttribute(Map map){
        //获取所有小区
        List<Community> communityList = communityService.findAll();
        //获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //获取楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("floorList",floorList);
        map.put("buildStructureList",buildStructureList);
        map.put("directionList",directionList);
        map.put("decorationList",decorationList);
        map.put("houseUseList",houseUseList);
    }

    //发布和取消发布
    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId")Long houseId,@PathVariable("status")Integer status){
        houseService.publish(houseId,status);
        return "redirect:/house";
    }

    //查询房源详情
    @RequestMapping("/{houseId}")
    public String show(@PathVariable("houseId")Long houseId,Map map){
        House house = houseService.getById(houseId);
        map.put("house",house);
        Community community = communityService.getById(house.getCommunityId());
        map.put("community",community);

        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId,1);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(houseId,2);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(houseId);
        //查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(houseId);

        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);
        return "house/show";
    }

}
