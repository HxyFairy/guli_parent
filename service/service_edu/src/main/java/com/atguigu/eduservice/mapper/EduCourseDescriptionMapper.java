package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程简介 Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduCourseDescriptionMapper extends BaseMapper<EduCourseDescription> {

    //根据课程id删除课程描述
    void deleteByDescriptionId(String courseId);
}
