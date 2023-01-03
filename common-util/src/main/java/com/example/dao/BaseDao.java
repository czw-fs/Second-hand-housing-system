package com.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: fs
 * @date: 2022/12/28 15:31
 * @Description: everything is ok
 */
public interface BaseDao<T> {

    /**
     * 保存一个实体
     *
     * @param t
     */
    Integer insert(T t);


    /**
     * 删除
     *
     * @param id 标识ID 可以是自增长ID，也可以是唯一标识。
     */
    void delete(Serializable id);

    /**
     * 更新一个实体
     *
     * @param t
     */
    Integer update(T t);

    /**
     * 通过一个标识ID 获取一个唯一实体
     *
     * @param id 标识ID 可以是自增长ID，也可以是唯一标识。
     * @return
     */
    T getById(Serializable id);

    List<T> findPage(Map<String, Object> filters);
}
