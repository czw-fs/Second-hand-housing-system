package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Admin;
import com.example.entify.HouseBroker;
import com.example.service.AdminService;
import com.example.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/1 9:39
 * @Description: everything is ok
 */
@Controller
@RequestMapping("houseBroker")
public class HouseBrokerController extends BaseController{

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    //去添加经纪人页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId")Long houseId, Map map){
        map.put("houseId",houseId);

        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);
        return "houseBroker/create";
    }

    //保存经纪人
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        houseBrokerService.insert(houseBroker);

        return "common/successPage";
    }

    //去修改经纪人的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id")Long id,Map map){
        HouseBroker houseBroker = houseBrokerService.getById(id);
        map.put("houseBroker",houseBroker);

        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);


        return "houseBroker/edit";
    }

    //更新经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());

        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    //删除经纪人
    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("brokerId")Long brokerId){
        houseBrokerService.delete(brokerId);


        return "redirect:/house/" + houseId;
    }
}
