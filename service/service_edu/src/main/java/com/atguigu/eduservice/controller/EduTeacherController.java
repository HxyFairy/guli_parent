package com.atguigu.eduservice.controller;




import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.atguigu.servicebase.exceptionhandler.GuliException;
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
 * 讲师 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-08-17
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1查询讲师表中的所有数据
    @ApiOperation(value = "查询所有讲师的列表")
    @GetMapping("/findAll")
    R FindAllTeacher(){
    //调用service里面的方法查询所有操作
        try {

        }catch (Exception e){
            //执行自定义异常
            throw  new GuliException(20001,"执行自定义异常处理");
        }
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("itms",list);
    }

    /**
     * 逻辑删除讲师的方法
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("delete/{id}")
    R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
       boolean b = teacherService.removeById(id);
       if (b){
        return R.ok();
       }else {
           return R.error();
       }
    }

    //分页查询方法
    @ApiOperation(value ="分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    R pageList(
               @ApiParam(name = "current" ,value = "当前页码",required = true)
               @PathVariable Long current,
               @ApiParam(name = "limit",value = "每页记录数",required = true)
               @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<>(current,limit);
        teacherService.page(pageParam,null);
        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //条件查询带分页的方法
    @ApiOperation(value ="条件查询带分页的方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(  @ApiParam(name = "current" ,value = "当前页码",required = true) @PathVariable Long current,
                                    @ApiParam(name = "limit",value = "每页记录数",required = true)  @PathVariable Long limit,
                                    @RequestBody(required = false) TeacherQuery teacherQurrey
                                    ){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //mybatis的sql动态
        String name = teacherQurrey.getName();
        String begin = teacherQurrey.getBegin();
        String end = teacherQurrey.getEnd();
        Integer level = teacherQurrey.getLevel();
        //判断条件之是否为空，如果不为空拼接条件  !StringUtils工具类
        if (!StringUtils.isEmpty(name)){
        //构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
        wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //调用方法实现查询分页
        teacherService.page(page,wrapper);

        long total = page.getTotal();//总记录数
        List<EduTeacher> records = page.getRecords();//数据list集合
        return R.ok().data("rows",records).data("total",total);
    }

    //添加讲师的接口方法
    @ApiOperation(value = "添加讲师的接口方法")
    @PostMapping("addTeacher")
    R addTeacher(
            @ApiParam(name = "eduteacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据讲师id继续进行查询
    @ApiOperation("根据讲师id继续进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        EduTeacher byId = teacherService.getById(id);
        return R.ok().data("eduteacher",byId);
    }

    @ApiOperation("讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "eduteacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


}

