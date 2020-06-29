package com.memorial.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author kgy
 * @version 1.0
 * @date 2019/12/13 14:44
 */
public class QiniuUtil {

    private String ACCESSKEY;

    private String SECRETKEY;

    private String BUCKETNAME;

    private String FILEDOMAIN;
/*    private static final String ACCESSKEY = "M3ZI4L50CRlfJVsB0ygqPw-3Ic79cHPCdXl52JAZ" ;

    private static final String SECRETKEY = "RZ94g0qHN5COrSyDP4BhumRgNI70p0CTv47EdiLB" ;

    private static final String BUCKETNAME = "memorial-imgs" ;

    private static final String FILEDOMAIN = "http://zhuisi.ioiogoo.cn/" ;*/

    private UploadManager manager;

    public QiniuUtil(String ACCESSKEY, String SECRETKEY, String BUCKETNAME, String FILEDOMAIN) {
        this.ACCESSKEY = ACCESSKEY;
        this.SECRETKEY = SECRETKEY;
        this.BUCKETNAME = BUCKETNAME;
        this.FILEDOMAIN = FILEDOMAIN;
        //初始化基本配置
        Configuration cfg = new Configuration(Zone.zone1());
        //创建上传管理器
        manager = new UploadManager(cfg);
    }

    public String upload(String imageName, byte[] bytes) {
        Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
        //构造覆盖上传token
        String upToken = auth.uploadToken(BUCKETNAME, imageName);
        try {
            Response response = manager.put(bytes, imageName, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return FILEDOMAIN + putRet.key;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }
}
