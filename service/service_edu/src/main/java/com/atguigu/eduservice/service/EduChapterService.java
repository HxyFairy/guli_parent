package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.excel.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoCourseId(String courseId);

    boolean deleteChapterId(String chapterId);
}
