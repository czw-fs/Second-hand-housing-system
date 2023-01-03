package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Community;
import com.example.entify.Dict;
import com.example.service.CommunityService;
import com.example.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/31 10:53
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String,Object> filters = getFilters(request);
        map.put("filters",filters);

        PageInfo<Community> page = communityService.findPage(filters);
        map.put("page",page);

        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);
        return "community/index";
    }

    @RequestMapping("/create")
    public String goAddPage(Map map){
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);
        return "community/create";
    }

    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String goEditPage(Map map ,@PathVariable("id") Long id){
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        map.put("areaList",areaList);

        Community community = communityService.getById(id);
        map.put("community",community);
        return "community/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        communityService.delete(id);
        return "redirect:/community";
    }
}
