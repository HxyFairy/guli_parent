package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.controller.EduCourseController;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService eduCourseService;
    //分页查询讲师的方法
    @PostMapping("getTeacherfront/{page}/{limit}")
    @ApiOperation("分页讲师的方法")
    public R getTeacherfront(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回分页的所有数据
        return R.ok().data("map",map).data("list",pageTeacher);
    }

    //讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    @ApiOperation("讲师的详情功能")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //根据讲师id查询讲师的基本信息
        EduTeacher byId = teacherService.getById(teacherId);
        //查询课程信息
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("list",list).data("teacher",byId);
    }
}
