package com.fh.brand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.service.IPmsBrandService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/pms/pmsBrand")
@Api(tags="PmsBrandController",description = "品牌管理")
public class PmsBrandController {

    @Autowired
    private IPmsBrandService pmsBrandService;

    /*查询品牌*/
    /*条件查询与分页*/
    @GetMapping
    @ApiOperation("查询品牌信息")
    @LoginAuthorization
    public CommonsReturn queryPageBrand(Page<PmsBrand> page) {
       /* QueryWrapper<PmsBrand> queryWrapper=new QueryWrapper<PmsBrand>();
        if(StringUtils.isNotBlank(search.getName())){
            queryWrapper.like("name",search.getName());
        }*/
        IPage<PmsBrand> iPage = pmsBrandService.page(page);
        return CommonsReturn.success(iPage);
    }

    /*品牌新增或者修改*/
    @PostMapping
    @ApiOperation("新增或修改品牌信息")
    @LoginAuthorization
    public CommonsReturn saveBrand(PmsBrand pmsBrand){
       pmsBrandService.saveOrUpdate(pmsBrand);
        return CommonsReturn.success();
    }

    /*根据id进行查询品牌*/
    @GetMapping("a/{id}")
    @ApiOperation("根据id查询品牌")
    @LoginAuthorization
    public CommonsReturn getBrandById(@PathVariable("id") Integer id){
       PmsBrand brand =  pmsBrandService.getById(id);
        return CommonsReturn.success(brand);
    }
    @PutMapping("/factory")
    @LoginAuthorization
    public CommonsReturn updateFactoryStatus(PmsBrand brand){
        pmsBrandService.updateById(brand);
        return CommonsReturn.success();
    }
    @PutMapping("/show")
    @LoginAuthorization
    public CommonsReturn updateShowStatus(PmsBrand brand){
        pmsBrandService.updateById(brand);
        return CommonsReturn.success();
    }
    /*查询品牌的下拉框*/
    @GetMapping("/all")
    @LoginAuthorization
    public CommonsReturn  getBrandAll(){
        List<PmsBrand> list = pmsBrandService.list();
        return CommonsReturn.success(list);
    }


}
