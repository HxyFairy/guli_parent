package com.atguigu.servicebase.exceptionhandler;
import com.atguigu.commonutils.R;
import com.guli.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//异常处理器
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R erro(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public R erro(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }
    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody//为了返回数据
    public R erro(GuliException e){
        log.error(e.getMessage());
//        log.error(exceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
