package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hxy
 * @since 2022-09-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private CrmBannerService crmBannerService;
    @Override
    public CrmBanner getBannerById(String id) {
        CrmBanner banner = baseMapper.getBannerById(id);
        return banner;

    }

    @Override
    public void updateBannerId(CrmBanner crmBanner) {
        baseMapper.updateById(crmBanner);
    }

    @Override
    public void deleteBanner(String id) {
        baseMapper.deleteById(id);
    }

    //查询所有banner
    @Override
    @Cacheable(key = "'selectIndexList'",value = "banner")
    public List<CrmBanner> selectIndexList() {
        //根据id进行降序排序
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //用last的方法进行拼接
        wrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
