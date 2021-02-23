package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloth.exception.PaidExecution;

import com.cloth.exception.PaidOperationException;
import com.cloth.pojo.PaidVO;
import com.cloth.service.IPaidService;
import com.cloth.entity.Paid;
import com.cloth.mapper.PaidMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class PaidServiceImpl extends ServiceImpl<PaidMapper, Paid> implements IPaidService {

    @Resource
    private PaidMapper paidMapper;
    @Override
    public List<Paid> findPaidListByCondition(String status, String name, String clothType, Integer userlist_id) {
        QueryWrapper<Paid> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userlist_id",userlist_id);
        return paidMapper.selectList(queryWrapper);
    }

    @Override
    public PaidExecution addPaid(PaidVO paidvo) {
        if(paidvo == null){
            return new PaidExecution(false,"添加租金信息为空");
        }
        try{
            Date date=paidvo.getDate();
            Paid paid=new Paid();
            BeanUtils.copyProperties(paidvo,paid);
            paid.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            int effectedNum = paidMapper.insert(paid);
            if(effectedNum < 1){
                return new PaidExecution(false,"添加租金信息失败");
            }
        }catch (Exception e){
            throw new PaidOperationException(e.toString());
        }
        return new PaidExecution(true);
    }

    @Override
    public PaidExecution deletePaid(Integer paidId) {
        if(paidId == null){
            return new PaidExecution(false,"删除租金信息Id为空");
        }
        try{
            int effectedNum = paidMapper.deleteById(paidId);
            if(effectedNum < 1){
                return new PaidExecution(false,"删除租金信息失败");
            }
        }catch (Exception e){
            throw new PaidOperationException(e.toString());
        }
        return new PaidExecution(true);
    }

    @Override
    public PaidExecution updatePaid(Paid paid) {
        if(paid == null){
            return new PaidExecution(false,"更新租金信息为空");
        }
        try{
            int effectedNum = paidMapper.updateById(paid);
            if(effectedNum < 1){
                return new PaidExecution(false,"更新租金信息失败");
            }
        }catch (Exception e){
            throw new PaidOperationException(e.toString());
        }
        return new PaidExecution(true);
    }

    @Override
    public List<Paid> getAllPaidList() {
        Page<Paid> page=new Page(1,5);
        IPage iPage=paidMapper.selectPage(page,new QueryWrapper<>());
        return iPage.getRecords();
    }
}
