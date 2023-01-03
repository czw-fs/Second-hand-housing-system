package com.example;

import com.example.dao.DictDao;
import com.example.entify.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: fs
 * @date: 2022/12/31 13:31
 * @Description: everything is ok
 */
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private DictDao dictDao;


    @Test
    public void test1(){
        List<Dict> listByParentId = dictDao.findListByParentId(1L);
        for (Dict dict : listByParentId) {
            System.out.println("dict = " + dict);
        }
    }
}
