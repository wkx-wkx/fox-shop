package com.fh.product.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.service.IPmsBrandService;
import com.fh.category.entity.PmsProductCategory;
import com.fh.category.service.IPmsProductCategoryService;
import com.fh.product.entity.PmsProduct;
import com.fh.product.entity.PmsProductBo;
import com.fh.product.service.IPmsProductService;
import com.fh.utils.CommonsReturn;
import com.fh.utils.authorization.LoginAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author wkx
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/pms/product/product")
public class PmsProductController {

    @Autowired
    private IPmsProductService pmsProductService;
    @Autowired
    private IPmsBrandService brandService;
    @Autowired
    private IPmsProductCategoryService categoryService;

    @GetMapping
    @LoginAuthorization
    public CommonsReturn queryPageProduct(Page<PmsProduct> page){
        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<PmsProduct> iPage = pmsProductService.page(page,queryWrapper);
        return CommonsReturn.success(iPage);
    }

    @PostMapping
    @LoginAuthorization
    public CommonsReturn saveOrUpdateProductData(@RequestBody PmsProductBo pmsProductBo){
        pmsProductService.createProduct(pmsProductBo);
        return CommonsReturn.success();
    }

    @GetMapping("/{id}")
    @LoginAuthorization
    public CommonsReturn getProductById(@PathVariable("id")Long id){
        PmsProductBo pmsProductBo =  pmsProductService.getProductById(id);
        return CommonsReturn.success(pmsProductBo);

    }
}
