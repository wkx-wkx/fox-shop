package com.fh.attr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.attr.entity.PmsProductAttributeCategory;
import com.fh.attr.service.IPmsProductAttributeCategoryService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms/attr/category")
@Api(tags="PmsBrandController",description = "属性和分类")
public class PmsProductAttributeCategoryController {

    @Autowired
    private IPmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @GetMapping
    @ApiOperation("查询商品类型信息")
    @LoginAuthorization
    public CommonsReturn queryPageGateList(Page<PmsProductAttributeCategory> page){
        IPage<PmsProductAttributeCategory> iPage = pmsProductAttributeCategoryService.page(page);
        return CommonsReturn.success(iPage);
    }

    @PostMapping
    @ApiOperation("新增或者修改商品类型信息")
    @LoginAuthorization
    public CommonsReturn saveOrUpdateCategory(PmsProductAttributeCategory pmsProductAttributeCategory){
        pmsProductAttributeCategoryService.saveOrUpdate(pmsProductAttributeCategory);
        return CommonsReturn.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id进行商品类型查询")
    @LoginAuthorization
    public CommonsReturn getArrtCategoryById(@PathVariable("id") Integer id){
        PmsProductAttributeCategory productAttributeCategory = pmsProductAttributeCategoryService.getById(id);
        return CommonsReturn.success(productAttributeCategory);
    }

    @GetMapping("/all")
    @ApiOperation("属性类型查询")
    @LoginAuthorization
    public CommonsReturn fetchAttrCategoryAll(){
        List<PmsProductAttributeCategory> list = pmsProductAttributeCategoryService.list();
        return CommonsReturn.success(list);

    }

}
