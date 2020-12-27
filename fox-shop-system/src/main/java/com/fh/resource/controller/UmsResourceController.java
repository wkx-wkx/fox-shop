package com.fh.resource.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.resource.entity.UmsResource;
import com.fh.resource.service.IUmsResourceService;
import com.fh.resourceCategory.entity.UmsResourceCategory;
import com.fh.resourceCategory.service.IUmsResourceCategoryService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/ums/resource")
public class UmsResourceController {

    @Autowired
    private IUmsResourceService resourceService;
    @Autowired
    private IUmsResourceCategoryService resourceCategoryService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn queryPageResource(Page<UmsResource> page){
        IPage<UmsResource> ipage = resourceService.page(page);
        return CommonsReturn.success(ipage);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn createResource(@RequestBody UmsResource resource){
        resourceService.saveOrUpdate(resource);
        return CommonsReturn.success();
    }

    @GetMapping("getResourceById")
    @LoginAuthorization
    public CommonsReturn getResourceById(Long id){
        UmsResource resource = resourceService.getById(id);
        return CommonsReturn.success(resource);
    }

    @DeleteMapping
    @LoginAuthorization
    public CommonsReturn deleteResource(Long id){
        resourceService.removeById(id);
        return CommonsReturn.success();
    }

    @GetMapping("fetchResourceSelect")
    @LoginAuthorization
    public CommonsReturn fetchResourceSelect(){
        List<UmsResourceCategory> list = resourceCategoryService.list();
        return CommonsReturn.success(list);
    }

}
