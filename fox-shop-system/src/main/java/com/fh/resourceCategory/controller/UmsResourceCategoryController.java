package com.fh.resourceCategory.controller;


import com.fh.resourceCategory.entity.UmsResourceCategory;
import com.fh.resourceCategory.service.IUmsResourceCategoryService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private IUmsResourceCategoryService resourceCategoryService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn listAllCate(UmsResourceCategory resourceCategory){
        List<UmsResourceCategory> list = resourceCategoryService.list();
        return CommonsReturn.success(list);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn saveOrUpdateResourceCategory(UmsResourceCategory resourceCategory){
        resourceCategoryService.saveOrUpdate(resourceCategory);
        return CommonsReturn.success();
    }

    @GetMapping("fetchResourceCategoryById")
    @LoginAuthorization
    public CommonsReturn fetchResourceCategoryById(@PathVariable Long id){
        UmsResourceCategory resourceCategory = resourceCategoryService.getById(id);
        return CommonsReturn.success(resourceCategory);
    }

    @DeleteMapping
    @LoginAuthorization
    public CommonsReturn deleteResourceCategory(@PathVariable Long id){
        resourceCategoryService.removeById(id);
        return CommonsReturn.success();
    }

}
