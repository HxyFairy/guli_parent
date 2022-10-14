package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        //根据课程id查询基本信息的方法
   public CoursePublishVo getPublishCourseInfo(String courseId);

    //根据课程id删除所有数据
    int deleteCourse(String courseId);

    CourseFrontVo getBaseCourseInfo(String courseId);
}
