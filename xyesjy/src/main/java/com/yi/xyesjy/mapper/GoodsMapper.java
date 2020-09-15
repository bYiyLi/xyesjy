package com.yi.xyesjy.mapper;

import com.yi.xyesjy.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<String> getAllType();
}
