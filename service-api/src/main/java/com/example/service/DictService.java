package com.example.service;

import com.example.entify.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/30 9:41
 * @Description: everything is ok
 */
public interface DictService {
    //查询数据字典中的数据,通过zTree进行渲染
    List<Map<String, Object>> findZnodes(Long id);

    //根据编码获取该节点下的所有子节点
    List<Dict> findListByParentId(Long parentId);

    //根据编码获取该节点下的所有子节点
    List<Dict> findListByDictCode(String dictCode);



}
