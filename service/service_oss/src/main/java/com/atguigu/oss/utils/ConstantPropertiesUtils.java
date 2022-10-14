package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//项目启动时，spring接口，spring加载后，执行一个接口的方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件里的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //定义公开静态的常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}
