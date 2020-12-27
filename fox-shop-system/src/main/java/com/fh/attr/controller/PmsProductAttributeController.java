package com.fh.attr.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.attr.entity.PmsProductAttribute;
import com.fh.attr.entity.PmsProductAttributeCategory;
import com.fh.attr.service.IPmsProductAttributeCategoryService;
import com.fh.attr.service.IPmsProductAttributeService;
import com.fh.model.search.AttrSearch;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms/attr/attribute")
public class PmsProductAttributeController {

    @Autowired
    private IPmsProductAttributeService productAttributeService;

    @GetMapping
    @ApiOperation("查询属性设置数据")
    @LoginAuthorization
    public CommonsReturn fetchAttrCategoryData(Page<PmsProductAttribute> page, AttrSearch search){
        QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(search.getName())){
            queryWrapper.like("name",search.getName());
        }
        queryWrapper.eq("product_attribute_category_id",search.getCid());
        queryWrapper.eq("type",search.getType());
        IPage<PmsProductAttribute> ipage = productAttributeService.page(page, queryWrapper);
        return CommonsReturn.success(ipage);
    }

    /*属性设置的新增或者修改*/
    @PostMapping
    @ApiOperation("属性设置的新增或修改")
    @LoginAuthorization
    public CommonsReturn saveOrUpdateCategory(PmsProductAttribute pmsProductAttribute){
        productAttributeService.saveOrUpdate(pmsProductAttribute);
        return CommonsReturn.success();
    }
    
    @GetMapping("/{id}")
    @ApiOperation("根据id查询属性设置")
    @LoginAuthorization
    @PreAuthorize("hasAuthority('pms:attr:value')")
    public CommonsReturn getArrtCategoryById(@PathVariable("id") Integer id){
        PmsProductAttribute pmsProductAttribute = productAttributeService.getById(id);
        return CommonsReturn.success(pmsProductAttribute);
    }
}
