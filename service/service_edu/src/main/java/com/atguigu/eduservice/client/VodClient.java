package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(value = "service-oss",fallback = VodFileDegradeFeignClient.class)//调用服务器的名称
public interface VodClient {
     //定义调用方法的路径
     @PostMapping("/eduoss/fileoss")
     public R uploadOssFile(MultipartFile file);

    }
