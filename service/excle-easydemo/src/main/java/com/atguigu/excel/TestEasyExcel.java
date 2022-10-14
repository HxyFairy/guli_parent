package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.atguigu.excel.listen.ExcelListener;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
         //实现excel的写的操作
        //1 设置写入文件夹的地址和excel文件名称
//    String filename="F:\\write.xlsx";
        //2.调用easyExcel里面的方法实现写操作
        //write两个参数：第一个参数文件路径名称参数，第二个参数实体类class
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());

        //实现excel的读操作
        String filename="F:\\write.xlsx";

        EasyExcel.read(filename,DemoData.class, new ExcelListener()).sheet().doRead();


    }
        //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            list.add(demoData);
        }
        return list;
    }
}
