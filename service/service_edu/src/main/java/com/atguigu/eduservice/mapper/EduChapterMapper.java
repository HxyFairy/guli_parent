package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    //根据课程id删除章节
    void  deleteByCourseId(String courseId);
}
