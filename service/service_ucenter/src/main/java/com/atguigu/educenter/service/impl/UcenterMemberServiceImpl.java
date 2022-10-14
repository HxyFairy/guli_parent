package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.JwtUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-10-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录的方法
    @Override
    public String login(UcenterMember ucenterMember) {
        //获取登陆的手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        //非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登陆失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查出来的对象是否为空
        if(mobileMember==null){
            throw new GuliException(20001,"手机号错误，登录失败");
        }
        //判断密码
        //因为存储到数据库中的密码肯定加密
        //把我们输入的密码进行加密，在和数据库进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码错误，登录失败");
        }
        //判断用户是否禁用
        if(mobileMember.getIsDisabled()==true){
            throw new GuliException(20001,"账号禁用，登录失败");
        }
        //登陆成功
        //生成token的字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(code)){
            throw new GuliException(20001,"账号不能为空");
        }
        //判断验证码
        String s = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(s)){
            throw new GuliException(20001,"验证码错误");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer>0){
            throw new GuliException(20001,"手机号重复");
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setIsDisabled(false);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
