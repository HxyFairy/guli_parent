package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.excel.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
@RestController
@RequestMapping("/eduservice/eduChapter")
@Api(description = "课程大纲")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @ApiOperation(value = "课程大纲列表根据id查询")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
      List<ChapterVo> list = eduChapterService.getChapterVideoCourseId(courseId);
      return R.ok().data("list",list);
    }

    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
            eduChapterService.save(eduChapter);
            return R.ok();
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }


    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation("删除的方法")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
       boolean i = eduChapterService.deleteChapterId(chapterId);
       if (i=true){
           return R.ok();
       }
       return R.error();

    }

}

