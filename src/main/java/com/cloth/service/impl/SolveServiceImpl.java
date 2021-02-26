package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloth.exception.SolveExecution;
import com.cloth.exception.SolveOperationException;
import com.cloth.service.ISolveService;
import com.cloth.entity.Solve;
import com.cloth.mapper.SolveMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloth.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@Service
public class SolveServiceImpl extends ServiceImpl<SolveMapper, Solve> implements ISolveService {

    @Resource
    SolveMapper solveMapper;

    @Override
    public List<Solve> findSolveListByCondition(String status, String name, String clothDetail, Integer userlist_id) {
        if(status==null&&name==null&&clothDetail==null&&userlist_id==null){
            return solveMapper.selectList(new QueryWrapper<Solve>());
        }
        QueryWrapper<Solve> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userlist_id",userlist_id).or()
                .eq("status",status).or()
                .eq("cloth_type",clothDetail).or()
                .eq("name",name);
        return solveMapper.selectList(queryWrapper);
    }

    @Override
    public SolveExecution addSolve(Solve solve) {
        if(solve == null){
            return new SolveExecution(false,"添加故障信息为空");
        }
        Date date = DateUtil.dateFormat1(new Date());
        solve.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());;
        solve.setStatus("未处理");
        try{
            int effectedNum = solveMapper.insert(solve);
            if(effectedNum < 1){
                return new SolveExecution(false,"添加故障信息失败");
            }
        }catch (Exception e){
            throw new SolveOperationException(e.toString());
        }
        return new SolveExecution(true);
    }

    @Override
    public SolveExecution deleteSolve(Integer id) {
        if(id == null){
            return new SolveExecution(false,"删除故障信息Id为空");
        }
        try{
            int effectedNum = solveMapper.deleteById(id);
            if(effectedNum < 1){
                return new SolveExecution(false,"删除故障信息失败");
            }
        }catch (Exception e){
            throw new SolveOperationException(e.toString());
        }
        return new SolveExecution(true);
    }

    @Override
    public SolveExecution updateSolve(Solve solve) {
        if(solve == null){
            return new SolveExecution(false,"更新故障信息为空");
        }
        try{
            int effectedNum = solveMapper.updateById(solve);
            if(effectedNum < 1){
                return new SolveExecution(false,"更新故障信息失败");
            }
        }catch (Exception e){
            throw new SolveOperationException(e.toString());
        }
        return new SolveExecution(true);
    }
}
