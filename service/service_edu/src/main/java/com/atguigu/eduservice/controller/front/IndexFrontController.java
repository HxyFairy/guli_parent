package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
@Autowired
private EduCourseService eduCourseService;
@Autowired
private EduTeacherService eduTeacherService;

    @GetMapping("index")
    @ApiOperation("查询8条讲师，四条热门课程")
    public R index(){
        //获取课程的前4条
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 4");
        List<EduCourse> eduCourseList = eduCourseService.list(eduCourseQueryWrapper);

        //获取讲师的前8条
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("eduCourseList",eduCourseList);
        map.put("eduTeacherList",eduTeacherList);
        return R.ok().data(map);
    }

}
