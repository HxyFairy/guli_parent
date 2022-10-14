package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.excel.chapter.ChapterVo;
import com.atguigu.eduservice.entity.excel.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoCourseId(String courseId) {
        //根据id查询里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter=new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        //根据id查询里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> VideoList = eduVideoService.list(wrapperVideo);
        //遍历查询章节进行封装
        List<ChapterVo> finalList= new ArrayList<>();
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapter = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapter);
            finalList.add(chapter);

            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历查询小节进行遍历封装
            for (int m = 0; m < VideoList.size(); m++) {
                    EduVideo eduVideo = VideoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo= new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapter.setChildren(videoVoList);
        }
        return finalList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapterId(String chapterId) {
        //根据chapterid章节ID 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //查出几条记录 count
        int count = eduVideoService.count(wrapper);
        if(count>0){//查询出小节不进行操作
            throw new GuliException(20001,"不能删除");
        }else{//不能查询出数据进行删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
}
