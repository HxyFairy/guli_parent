package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hxy
 * @since 2022-10-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //登陆的方法
    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);
}
