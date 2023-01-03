package com.example;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author: fs
 * @date: 2023/1/1 13:57
 * @Description: everything is ok
 */

/*
 *Zone.zone0:华东
 *Zone.zone1:华北
 *Zone.zone2:华南
 */
public class qiniuTest {
    @Test
    public void test1(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        //去个人中心,密钥管理中将AK和SK拷贝过来
        String accessKey = "YrNRJryIwW1mMIFGoafbJxbPrIVy7qgNndQievhZ";
        String secretKey = "FeueHhpU328oPSI6wvWkn7Os0OY42wglbXMs8J3Q";
        //设置你创建空间的名字
        String bucket = "shf-fs";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //设置本地文件额路径
//        String localFilePath = "D:\\OneDrive\\桌面\\fs\\photo\\ff.jpeg";
        String localFilePath = "D:/OneDrive/桌面/fs/photo/pp.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "风生";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }

    @Test
    public void testdelete(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        String accessKey = "YrNRJryIwW1mMIFGoafbJxbPrIVy7qgNndQievhZ";
        String secretKey = "FeueHhpU328oPSI6wvWkn7Os0OY42wglbXMs8J3Q";

        String bucket = "shf-fs";
        String key = "FogLAo9PBcGR3VqS5GfyVtP9l_SC";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

    }
}
