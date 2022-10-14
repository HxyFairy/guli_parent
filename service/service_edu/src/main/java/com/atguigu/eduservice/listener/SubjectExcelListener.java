package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService EduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService EduSubjectService) {
        this.EduSubjectService = EduSubjectService;
    }

    //一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(2001,"文件数据为空");
        }

        //一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否成功
        //调用那个方法
        EduSubject oneSubject = this.existOneSubject(EduSubjectService, subjectData.getOneSubjectName());
        if(oneSubject==null){//没有相同的一级分类，进行添加
            oneSubject= new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            EduSubjectService.save(oneSubject);
        }

        //获取一级分类的id值
        String pid=oneSubject.getId();

        //添加二级分类
        //判断二级分类是否重复
        EduSubject twoSubject = this.existTwoSubject(EduSubjectService, subjectData.getTwoSubjectName(), pid);

        if(twoSubject==null){
           twoSubject= new EduSubject();
           twoSubject.setParentId(pid);
           twoSubject.setTitle(subjectData.getTwoSubjectName());
           EduSubjectService.save(twoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService EduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject one = EduSubjectService.getOne(queryWrapper);
        return one;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService EduSubjectService,String name,String pid){
QueryWrapper<EduSubject> wrapper =new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject Two = EduSubjectService.getOne(wrapper);
        return Two;
    }



    //结束时候用的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
