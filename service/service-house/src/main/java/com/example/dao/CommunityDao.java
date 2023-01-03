package com.example.dao;

import com.example.entify.Community;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 10:39
 * @Description: everything is ok
 */
public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
