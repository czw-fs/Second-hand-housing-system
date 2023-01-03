package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.DictDao;
import com.example.entify.Dict;
import com.example.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/30 9:42
 * @Description: everything is ok
 */
@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService{

    @Autowired
    private DictDao dictDao;
    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //根据父id查询该节点下的所有子节点
        List<Dict> dictList = dictDao.findListByParentId(id);
        //创建返回的list
        List<Map<String,Object>> zNodes = new ArrayList<>();
        //遍历dictList
        for(Dict dict : dictList){
            //创建有一个map
            Map map = new HashMap();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            //判断该节点是否是父节点
            Integer count = dictDao.isParentNode(dict.getId());
            map.put("isParent",count > 0 ? true : false);

            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getDictByDictCode(dictCode);
        if(dict == null) return null;

        List<Dict> listByParentId = this.findListByParentId(dict.getId());
        return listByParentId;
    }
}
