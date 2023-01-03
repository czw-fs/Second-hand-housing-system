package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.*;
import com.example.entify.UserFollow;
import com.example.result.Result;
import com.example.result.ResultCodeEnum;
import com.example.service.*;
import com.example.util.MD5;
import com.example.vo.HouseQueryVo;
import com.example.vo.HouseVo;
import com.example.vo.RegisterVo;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/1 17:05
 * @Description: everything is ok
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private UserInfoService userInfoService;

    @Reference
    private UserFollowService userFollowService;
    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    //分页及带条件的查询方法
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum")Integer pageNum, @PathVariable("pageSize")Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    //查看房源详情
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id")Long id,HttpSession session){
        House house = houseService.getById(id);
        Community CommunityName = communityService.getById(house.getCommunityId());
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);

        Map map = new HashMap();

        map.put("house",house);
        map.put("community",CommunityName);
        map.put("houseImage1List",houseImage1List);
        map.put("houseBroker",houseBrokerList);

//        map.put("isFollow",false);

        Boolean isFollowed = false;
        UserInfo userInfo = (UserInfo)session.getAttribute("user");
        if(userInfo != null){
            isFollowed = userFollowService.isFollowed(userInfo.getId(),id);
        }
        map.put("isFollow",isFollowed);
        return Result.ok(map);
    }



}
