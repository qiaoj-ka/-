package com.cloth.service;

import com.cloth.entity.Paid;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloth.exception.PaidExecution;
import com.cloth.pojo.PaidVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface IPaidService extends IService<Paid> {

    List<Paid> findPaidListByCondition(String status, String name, String clothType, Integer userlist_id);

    PaidExecution addPaid(PaidVO paid);

    PaidExecution deletePaid(Integer paidId);

    PaidExecution updatePaid(Paid paid);

    List<Paid> getAllPaidList();
}
