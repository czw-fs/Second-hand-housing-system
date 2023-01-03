package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.HouseImage;
import com.example.result.Result;
import com.example.service.HouseImageService;
import com.example.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: fs
 * @date: 2023/1/1 14:30
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    //去上传图片的界面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Map map) {
        map.put("houseId", houseId);
        map.put("type", type);
        return "house/upload";
    }

    //添加房源或房产图片
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) {
        //获取字节数组
        try {

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    //获取字节数组
                    byte[] bytes = file.getBytes();
                    //获取图片名字
                    String originalFilename = file.getOriginalFilename();
                    //通过UUID随机生成一个字符串作为文件的名字
                    String newFileName = UUID.randomUUID().toString();
                    //通过QiniuUtil工具上传图片到七牛云
                    QiniuUtils.upload2Qiniu(bytes,newFileName);

                    //创建HouseImage对象
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    //设置图片的路径 路径格式: http:/七牛云的域名/随机生成的图片的名字
                    houseImage.setImageUrl("http://rnshg0gyu.hn-bkt.clouddn.com/" + newFileName);
                    houseImageService.insert(houseImage);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    //删除房源或房产图片
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("id")Long id){
        houseImageService.delete(id);
        return "redirect:/house/" + houseId;
    }

}

