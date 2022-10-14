package com.atguigu.educenter.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.JwtUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-10-10
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
 private UcenterMemberService ucenterMemberService;

    @ApiOperation("登錄")
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember){
        //member对象封装手机号和密码
    //调用service方法实现登录
    //返回token值，使用jwt生成
   String token =ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return R.ok().data("item",ucenterMember);
    }
}

