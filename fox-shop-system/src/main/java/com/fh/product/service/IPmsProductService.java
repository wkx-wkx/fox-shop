package com.fh.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fh.product.entity.PmsProduct;
import com.fh.product.entity.PmsProductBo;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author wkx
 * @since 2020-12-13
 */
public interface IPmsProductService extends IService<PmsProduct> {

    void createProduct(PmsProductBo pmsProductBo);

    PmsProductBo getProductById(Long id);
}
