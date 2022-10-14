package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/eduservice/subject")
@Api(description = "课程分类管理")
@CrossOrigin//解决跨域问题
public class EduSubjectController {
    @Autowired
    private EduSubjectService EduSubjectService;

    //添加课程分类
    //获取到上传的文件，把文件内容读取出来
    @ApiOperation("添加课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的excel文件
    EduSubjectService.saveSubject(file,EduSubjectService);
    return R.ok();
    }

    //课程分类的列表功能，树形结构
    @ApiOperation("课程分类的列表功能，树形结构")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
      List<OneSubject> list = EduSubjectService.getAllSubject();
      return R.ok().data("list",list);
    }

}

