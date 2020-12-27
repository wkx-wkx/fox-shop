package com.fh.product.service.impl;

import com.fh.product.entity.PmsProductFullReduction;
import com.fh.product.mapper.PmsProductFullReductionMapper;
import com.fh.product.service.IPmsProductFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author wkx
 * @since 2020-12-13
 */
@Service
public class PmsProductFullReductionServiceImpl extends ServiceImpl<PmsProductFullReductionMapper, PmsProductFullReduction> implements IPmsProductFullReductionService {

}
