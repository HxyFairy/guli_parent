package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class CourseQuery implements Serializable {
    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程状态 Draft未发布  Normal已发布")
    private String status;

}
