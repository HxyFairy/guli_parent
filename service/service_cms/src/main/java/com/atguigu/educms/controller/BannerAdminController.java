package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author hxy
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
@Api(description = "后台banner的管理接口")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    @ApiOperation("分页查询banner")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    //增加banner
    @PostMapping("save")
    @ApiOperation("新增banner")
    public R save(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //获取bannner
    @GetMapping("get/{id}")
    @ApiOperation("获取banner")
    public R getId(@PathVariable String id){
      CrmBanner crmBanner =  crmBannerService.getBannerById(id);
        return R.ok().data("list",crmBanner);
    }

    //修改
    @PostMapping("update")
    @ApiOperation("修改bannner")
    public R update(@RequestBody CrmBanner crmBanner){
         crmBannerService.updateBannerId(crmBanner);
        return R.ok();
    }

    //删除
    @DeleteMapping("delete/{id}")
    @ApiOperation("删除bannner")
    public R deleteId(@PathVariable String id ){
       crmBannerService.deleteBanner(id);
        return R.ok();
    }



}
