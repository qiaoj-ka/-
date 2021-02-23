package com.cloth.service;

import com.cloth.entity.Solve;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloth.exception.SolveExecution;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface ISolveService extends IService<Solve> {

    List<Solve> findSolveListByCondition(String status, String name, String address, Integer userlist_id);

    SolveExecution addSolve(Solve solve);

    SolveExecution deleteSolve(Integer id);

    SolveExecution updateSolve(Solve solve);
}
