package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    //根据课程id删除小节
   void deleteEduVideoCourseId(String courseId);

}
