package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-09-11
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
@Api(description = "小节")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    //添加小节
    @PostMapping("addVideo")
    @ApiOperation("添加小节")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO 后面这个方法需要进行完善：删除小节的时候，同时把里面视频删除
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        eduVideoService.removeById(id);
        return R.ok();
    }
    //修改小节
    @ApiOperation("根据id查询小节数据")
    @GetMapping("getVideo/{id}")
    public R getVideoId(@PathVariable String id){
        EduVideo byId = eduVideoService.getById(id);
        return R.ok().data("byid",byId);
    }
    @ApiOperation("修改小节数据")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }


}

