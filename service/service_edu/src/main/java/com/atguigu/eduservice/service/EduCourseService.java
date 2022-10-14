package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息的基本方法
    void saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程的基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo getPublishCourseInfo(String courseId);

    void removeCourse(String courseId);
    //1.条件查询分页查询课程前台
    Map<String, Object> getCourseFrontList(Page<EduCourse> pages, CourseFrontVo courseFrontVo);

    //根据课程id，编写sql语句查询课程信息
    CourseFrontVo getBaseCourseInfo(String courseId);
}
