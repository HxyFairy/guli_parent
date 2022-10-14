package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-08-17
 */
@Service
public class
EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();//总记录数
        long pages = pageTeacher.getPages();//总页数
        long current = pageTeacher.getCurrent();//当前页
        long size = pageTeacher.getSize();//每页记录数
        boolean b = pageTeacher.hasNext();//当前是否有下一页
        boolean b1 = pageTeacher.hasPrevious();//当前是否有上一页

        //把分页数据获取出来，放到map集合中
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("pages",pages);
        map.put("current",current);
        map.put("size",size);
        map.put("b",b);
        map.put("b1",b1);

        return map;
    }
}
