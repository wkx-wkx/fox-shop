package com.fh.category.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.category.entity.PmsProductCategory;
import com.fh.category.service.IPmsProductCategoryService;
import com.fh.model.search.CategorySearch;
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
 * 产品分类 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/pms/category")
@Api(tags="PmsBrandController",description = "商品分类")
public class PmsProductCategoryController {

    @Autowired
    private IPmsProductCategoryService categoryService;

    /*查询商品分类*/
    /*条件查询与分页*/
    @GetMapping
    @ApiOperation("查询商品分类信息")
    @LoginAuthorization
     public CommonsReturn queryPageCategory(Page<PmsProductCategory> page, CategorySearch categorySearch){
        QueryWrapper<PmsProductCategory> queryWrapper=new QueryWrapper<PmsProductCategory>();
        if(categorySearch.getPid() != null){
            queryWrapper.eq("parent_Id",categorySearch.getPid());
        }else{
            queryWrapper.eq("level",categorySearch.getLevel());
        }
        if(StringUtils.isNotBlank(categorySearch.getName())){
            queryWrapper.like("name",categorySearch.getName());
        }
        IPage<PmsProductCategory> iPage = categoryService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }
    /*新增或修改分类商品*/
    @PostMapping
    @LoginAuthorization
    public CommonsReturn saveCategory(PmsProductCategory category){
        Long parentId=category.getParentId();
        if(parentId == 0){
            category.setLevel(0);
        }else{
            category.setLevel(1);
        }
        categoryService.saveOrUpdate(category);
        return  CommonsReturn.success();
    }

    @PutMapping("/show")
    @LoginAuthorization
    public CommonsReturn updateShowStatus(PmsProductCategory category){
        categoryService.updateById(category);
        return CommonsReturn.success();
    }
    @PutMapping("/nav")
    @LoginAuthorization
    public CommonsReturn updateNavStatus(PmsProductCategory category){
        categoryService.updateById(category);
        return CommonsReturn.success();
    }
    @GetMapping("/{id}")
    @LoginAuthorization
    public CommonsReturn getCategoryById(@PathVariable("id") Long id){
        PmsProductCategory category = categoryService.getById(id);
        return CommonsReturn.success(category);

    }

    //查询商品新增下拉框的数据
    @GetMapping("/all")
    @LoginAuthorization
    public CommonsReturn getCategoryAll(){
        List<Map<String,Object>> list=categoryService.getCategoryAll();
        return CommonsReturn.success(list);
    }

}
