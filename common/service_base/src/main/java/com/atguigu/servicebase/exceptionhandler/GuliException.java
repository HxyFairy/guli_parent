package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuliException extends RuntimeException{
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String msg;

}
