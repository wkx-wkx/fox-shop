package com.fh.product.entity;

import lombok.Data;

import java.util.List;

/**
 * @author wkx
 * @create 2020-12-15 19:10
 */
@Data
public class PmsProductBo extends PmsProduct{

    //会员价格集合
    private List<PmsMemberPrice> memberPriceList;
    //阶梯价格维护
    private List<PmsProductLadder> productLadderList;
    //满减价格
    private List<PmsProductFullReduction> productFullReductionList;
    //SKU的维护
    private List<PmsSkuStock> skuStockList;
    //商品的属性和参数列表
    private List<PmsProductAttributeValue> productAttributeValueList;

}
