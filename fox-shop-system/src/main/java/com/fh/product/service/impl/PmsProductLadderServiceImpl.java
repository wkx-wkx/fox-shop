package com.fh.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.product.entity.PmsProductLadder;
import com.fh.product.mapper.PmsProductLadderMapper;
import com.fh.product.service.IPmsProductLadderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 * @author wkx
 * @since 2020-12-13
 */
@Service
public class PmsProductLadderServiceImpl extends ServiceImpl<PmsProductLadderMapper, PmsProductLadder> implements IPmsProductLadderService {

}
