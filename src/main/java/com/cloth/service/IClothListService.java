package com.cloth.service;

import com.cloth.entity.ClothList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloth.exception.ClothExecution;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface IClothListService extends IService<ClothList> {
    List<ClothList> clothList();

    List<ClothList> findClothListByCondition(String status, String detail, Integer userlistId);

    ClothExecution addCloth(ClothList clothList);

    ClothExecution updateCloth(ClothList clothList);

    ClothExecution deleteCloth(Integer clothId);
}
