package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoMapper eduVideoMapper;
    @Autowired
    private EduChapterMapper eduChapterMapper;
    //    @Autowired
//    private EduCourseMapper eduCourseMapper;
    @Autowired
    private EduCourseDescriptionMapper courseDescriptionMapper;

    //添加课程基本信息的方法
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 像课程表里添加基本信息
        //CourseInfoVo转换成educourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }
        String cid = eduCourse.getId();
        //2 像课程简介表中添加课程简介信息
        //edu_course_description
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
    }


    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }


    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        eduVideoMapper.deleteEduVideoCourseId(courseId);

        //根据课程id删除章节
        eduChapterMapper.deleteByCourseId(courseId);
        //根据课程id删除课程描述
        courseDescriptionMapper.deleteByDescriptionId(courseId);
        //根据课程id删除课程本身
        int result = baseMapper.deleteCourse(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    //1.条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pages, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if (StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {//一级分类
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {//二级分类
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {//关注度
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {//最新
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            queryWrapper.orderByDesc("price");}
            baseMapper.selectPage(pages, queryWrapper);
            List<EduCourse> records = pages.getRecords();
            long current = pages.getCurrent();
            long page = pages.getPages();
            long size = pages.getSize();
            long total = pages.getTotal();
            boolean hasNext = pages.hasNext();
            boolean hasPrevious = pages.hasPrevious();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", records);
            map.put("current", current);
            map.put("pages", page);
            map.put("size", size);
            map.put("total", total);
            map.put("hasNext", hasNext);
            map.put("hasPrevious", hasPrevious);
            return map;
        }

    //根据课程id，编写sql语句查询课程信息
    @Override
    public CourseFrontVo getBaseCourseInfo(String courseId) {
        return  baseMapper.getBaseCourseInfo(courseId);
    }
}

