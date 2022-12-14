package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.excel.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService eduChapterService;
    //1.条件查询带分页查询课程
    @ApiOperation("条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pages = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pages,courseFrontVo);
        //返回分页的所有数据
        return R.ok().data(map).data("list",pages);
    }

    @ApiOperation("课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //根据课程id，编写sql语句查询课程信息
       CourseFrontVo courseFrontVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoCourseId = eduChapterService.getChapterVideoCourseId(courseId);
        return R.ok().data("courseFrontVo",courseFrontVo).data("chapterVideoCourseId",chapterVideoCourseId);
    }
}
