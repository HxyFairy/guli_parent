package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import javafx.scene.chart.XYChart;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //上传头像到Oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //获取阿里云存储相关的常量,工具类取值
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String endPoint = ConstantPropertiesUtils.END_POINT;

        try {
            //创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeySecret, accessKeyId, bucketName);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String originalFilename = file.getOriginalFilename();

            //1.在文件名称里面添加随机唯一的值
            //wer4-43u5-323oo-
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            originalFilename= uuid+originalFilename;

            //2.把文件按照日期进行分类
            //2019/11/12/01.jpg
            //获取当前的日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            //2019/11/12/01.jpg
            originalFilename= datePath+"/"+originalFilename;


            //调用oss的方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称
            //第三个参数 上传文件的输入流
            ossClient.putObject(bucketName,originalFilename,inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //把上传之后的文件的路径进行返回
            //需要把上传到阿里云oss路径手动拼接出来
        String url="http://"+bucketName+"."+endPoint+"/"+file;
        return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
