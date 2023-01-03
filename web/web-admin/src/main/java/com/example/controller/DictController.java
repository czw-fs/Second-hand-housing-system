package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Dict;
import com.example.result.Result;
import com.example.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/30 9:36
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //去展示数据字典的页面
    @RequestMapping
    public String index(){
        return "dict/index";
    }

    //获取数据字典中的数据
    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0")Long id){
        //查询数据字典这种数据的方法
        List<Map<String,Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    //根据父id获取所有子节点
    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result findListByParentId(@PathVariable("areaId")Long id){
        List<Dict> listByParentId = dictService.findListByParentId(id);
        return Result.ok(listByParentId);
    }
}
