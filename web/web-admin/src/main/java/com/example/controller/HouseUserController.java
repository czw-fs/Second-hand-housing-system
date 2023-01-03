package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.HouseUser;
import com.example.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author: fs
 * @date: 2023/1/1 11:18
 * @Description: everything is ok
 */
@Controller
@RequestMapping("houseUser")
public class HouseUserController extends BaseController{

    @Reference
    private HouseUserService houseUserService;

    //去添加房东的页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId")Long houseId , Map map){

        map.put("houseId",houseId);

        return "houseUser/create";
    }

    //添加
    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    //去修改页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id")Long id,Map map){
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser",houseUser);
        return "houseUser/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("houseUserId")Long houseUserId){
        houseUserService.delete(houseUserId);
        return "redirect:/house/" + houseId;
    }
}
