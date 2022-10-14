package com.atguigu.educms.mapper;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 首页banner表 Mapper 接口
 * </p>
 *
 * @author hxy
 * @since 2022-09-27
 */
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {
   public CrmBanner getBannerById(String id);
}
