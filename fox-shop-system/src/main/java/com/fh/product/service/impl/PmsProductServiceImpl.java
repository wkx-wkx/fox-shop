package com.fh.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.product.entity.*;
import com.fh.product.mapper.PmsProductMapper;
import com.fh.product.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author wkx
 * @since 2020-12-13
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Autowired
    private IPmsMemberPriceService memberPriceService;
    @Autowired
    private IPmsProductLadderService ladderService;
    @Autowired
    private IPmsSkuStockService skuStockService;
    @Autowired
    private IPmsProductFullReductionService fullReductionService;
    @Autowired
    private IPmsProductAttributeValueService attributeValueService;

    @Override
    @Transactional
    public void createProduct(PmsProductBo pmsProductBo) {
        PmsProduct product = pmsProductBo;
        boolean status = super.saveOrUpdate(product);
        if(status){
            Long productId = product.getId();
            //保存会员价格
           saveMemberPrice(pmsProductBo.getMemberPriceList(),productId);
            //阶梯价格
           saveProductLadder(pmsProductBo.getProductLadderList(),productId);
            //满减价格
           saveFullReduction(pmsProductBo.getProductFullReductionList(),productId);
            //SKU维护
            saveSkuStock(pmsProductBo.getSkuStockList(),productId);
            //商品的属性和参数列表
            saveProductAttributeValue(pmsProductBo.getProductAttributeValueList(),productId);
        }
    }

    private void saveProductLadder(List<PmsProductLadder> productLadderList, Long productId) {
        productLadderList.forEach(ladder->{
            ladder.setProductId(productId);
        });
        ladderService.remove(new QueryWrapper<PmsProductLadder>().eq("product_id",productId));
        ladderService.saveBatch(productLadderList);
    }

    public void saveMemberPrice(List<PmsMemberPrice> list,Long productId){
        list.forEach(memberPrice ->{
            memberPrice.setProductId(productId);
        });
        memberPriceService.remove(new QueryWrapper<PmsMemberPrice>().eq("product_id",productId));
        memberPriceService.saveBatch(list);
    }

    public void saveFullReduction(List<PmsProductFullReduction> productFullReductionList,Long productId){
        productFullReductionList.forEach(pmsProductFullReduction ->{
            pmsProductFullReduction.setProductId(productId);
        });
        fullReductionService.remove(new QueryWrapper<PmsProductFullReduction>().eq("product_id",productId));
        fullReductionService.saveBatch(productFullReductionList);
    }
    public void saveProductAttributeValue(List<PmsProductAttributeValue> productAttributeValueList,Long productId){
        productAttributeValueList.forEach(pmsProductAttributeValue -> {
            pmsProductAttributeValue.setProductId(productId);
        });
        attributeValueService.remove(new QueryWrapper<PmsProductAttributeValue>().eq("product_id",productId));
        attributeValueService.saveBatch(productAttributeValueList);

    }
    public void saveSkuStock(List<PmsSkuStock> skuStockList,Long productId){
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock pmsSkuStock = skuStockList.get(i);
            pmsSkuStock.setProductId(productId);
            String dateStr = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String productCode = String.format("%06d",productId);
            String skuCode = String.format("%03d",i+1);
            pmsSkuStock.setSkuCode(StringUtils.join(dateStr,productCode,skuCode));
        }
        skuStockService.remove(new QueryWrapper<PmsSkuStock>().eq("product_id",productId));
        skuStockService.saveBatch(skuStockList);
    }

    @Override
    public PmsProductBo getProductById(Long id) {
        //查询商品信息
        PmsProductBo pmsProductBo = new PmsProductBo();
        BeanUtils.copyProperties(super.getById(id),pmsProductBo);
        Map<String, Object> map = new HashMap<>();
        map.put("product_id",id);
        //保存会员价格
        List<PmsMemberPrice> pmsMemberPrices = memberPriceService.listByMap(map);
        //阶梯价格
        List<PmsProductLadder> pmsProductLadders = ladderService.listByMap(map);
        //满减价格
        List<PmsProductFullReduction> pmsProductFullReductions = fullReductionService.listByMap(map);
        //SKU维护
        List<PmsSkuStock> pmsSkuStocks = skuStockService.listByMap(map);
        //商品的属性和参数列表
        List<PmsProductAttributeValue> pmsProductAttributeValues = attributeValueService.listByMap(map);
        pmsProductBo.setMemberPriceList(pmsMemberPrices);
        pmsProductBo.setProductAttributeValueList(pmsProductAttributeValues);
        pmsProductBo.setProductFullReductionList(pmsProductFullReductions);
        pmsProductBo.setProductLadderList(pmsProductLadders);
        pmsProductBo.setSkuStockList(pmsSkuStocks);
        return pmsProductBo;
    }

}
