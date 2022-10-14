package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author hxy
 * @since 2022-09-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    CrmBanner getBannerById(String id);

    void updateBannerId(CrmBanner crmBanner);

    void deleteBanner(String id);

    List<CrmBanner> selectIndexList();
}
