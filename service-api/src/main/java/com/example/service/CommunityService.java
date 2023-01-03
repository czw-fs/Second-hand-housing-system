package com.example.service;

import com.example.entify.Community;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 10:49
 * @Description: everything is ok
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
