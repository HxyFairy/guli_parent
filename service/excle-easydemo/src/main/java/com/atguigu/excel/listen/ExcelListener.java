package com.atguigu.excel.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.excel.DemoData;


import java.util.List;
import java.util.Map;

//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {


    //一行一行的读取excel内容
    public void invoke(DemoData DemoData, AnalysisContext analysisContext) {
        System.out.println("**********"+DemoData);

    }

    //读取表头内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }


    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
