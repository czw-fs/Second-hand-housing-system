package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Dict;
import com.example.result.Result;
import com.example.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: fs
 * @date: 2023/1/1 16:41
 * @Description: everything is ok
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    //根据编码获取子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode")String dictCode){
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    //根据父id查询所有子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId")Long areaId){
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }

}
