package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Api(description = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("课程的基本实现")
    @GetMapping("/findAll")
    public R getCourse() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }

    @ApiOperation("分页查询")
    @GetMapping("pageLimit/{page}/{limit}")
    public R pageLimit(@ApiParam(name = "page", defaultValue = "1", value = "当前页码", required = true)
                       @PathVariable long page,
                       @ApiParam(name = "limit", required = true, value = "每页记录的数")
                       @PathVariable Long limit) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        courseService.page(eduCoursePage, null);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    @ApiOperation("根据课程名称和课程状态进行条件查询")
    @GetMapping("pageCourse")
    public R pageCourse(
//    @ApiParam(name = "page",defaultValue = "1",value = "当前页码",required = true)
//                        Long page,
//                        @ApiParam(name = "limit",defaultValue = "2",value="每页记录数",required = true)
//                        Long limit,
            @ApiParam(name = "CourseQuery", required = true, value = "查询对象")
                    CourseQuery courseQuery
    ) {
        Page<EduCourse> eduCoursePage = new Page<EduCourse>(1, 3);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("Draft", status);
        }
        courseService.page(eduCoursePage, wrapper);


        List<EduCourse> records = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        return R.ok().data("records", records).data("total", total);
    }



    @ApiOperation("课程删除")
    @DeleteMapping("delete/{courseId}")
    public R deleteId(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }


    //添加课程基本信息的方法
    @PostMapping("/addCourseInfo")
    @ApiOperation("添加课程基本信息的方法")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程的基本信息
    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation("根据课程id查询课程的基本信息")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }



    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }

    @ApiOperation("课程最终发布，修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
}

