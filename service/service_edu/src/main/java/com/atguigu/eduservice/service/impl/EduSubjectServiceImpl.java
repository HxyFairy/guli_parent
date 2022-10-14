package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-09-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService EduSubjectService) {
    try {
        //文件输入流
        InputStream inputStream = file.getInputStream();
        //调用方法进行读取
        EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(EduSubjectService)).sheet().doRead();
    }catch (Exception e){
    e.printStackTrace();
}


    }

    //树形结构
    @Override
    public List<OneSubject> getAllSubject() {
        //查询一级分类的数据
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneList = baseMapper.selectList(oneWrapper);
        //查询二级分类
        QueryWrapper<EduSubject> twoWrapper= new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoList = baseMapper.selectList(twoWrapper);
        //创建list的集合，用存储最终的封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //对一级分类进行封装,查询出来所有一级分类list集合进行遍历，得到每一个一级分类对象，获取每个一级分类的对象值
        for (int i = 0; i < oneList.size(); i++) {
            EduSubject eduSubject = oneList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
//        在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubject = new ArrayList<>();
            for (int i1 = 0; i1 < twoList.size(); i1++) {
                //获取我们的每个二级分类
                EduSubject tSubject1 = twoList.get(i1);
                //判断二级分类的parentid与以及分了i是否一样
                if (tSubject1.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject1,twoSubject);
                    twoFinalSubject.add(twoSubject);
                }

            }
            //把一级分类下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubject);
        }
        return finalSubjectList;
    }
}
