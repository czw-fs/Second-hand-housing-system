package com;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: fs
 * @date: 2023/1/3 12:16
 * @Description: everything is ok
 */
public class example {


    /*

        * */
    @Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //对111111进行第一次加密
        String encode = bCryptPasswordEncoder.encode("111111");
        System.out.println("encode = " + encode);

//        进行密码匹配
        boolean matches1 = bCryptPasswordEncoder.matches("111111", "$2a$10$zaRVI77HmCCGs6J6UppdKuZIRM5zaEwVfmA6y.QOnHCMXlR7XcCwu");
        System.out.println("matches = " + matches1);

        boolean matches2 = bCryptPasswordEncoder.matches("111111", "$2a$10$7oztYp2cwYXFGWlnSBfoX.BsNxGBj1dyT5Vx2M0XC2n3GT2.R3dte");
        System.out.println("matches = " + matches2);

    }
}
