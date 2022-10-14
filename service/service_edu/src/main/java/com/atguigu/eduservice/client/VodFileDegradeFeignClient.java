package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //出错之后才会执行
    @Override
    public R uploadOssFile(MultipartFile file) {
        return R.error().message("下载出错");
    }
}
