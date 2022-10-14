package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hxy
 * @since 2022-08-17
 */
public interface EduTeacherService extends IService<EduTeacher> {
//分页讲师查询
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
