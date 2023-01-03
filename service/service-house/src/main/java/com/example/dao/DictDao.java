package com.example.dao;

import com.example.entify.Dict;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/30 9:43
 * @Description: everything is ok
 */
public interface DictDao {
    //根据父id查询该节点下的所有子节点
    List<Dict> findListByParentId(Long id);

    //判断是否是父节点
    Integer isParentNode(Long id);

    //根据编码获取Dict对象
    Dict getDictByDictCode(String dictCode);

    //根据id获取name
    String getNameById(Long id);
}
