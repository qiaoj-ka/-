package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloth.exception.ScheduleExecution;
import com.cloth.exception.ScheduleOperationException;
import com.cloth.service.IScheduleService;
import com.cloth.entity.Schedule;
import com.cloth.mapper.ScheduleMapper;
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
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> findByCondition() {
        QueryWrapper<Schedule> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        Page<Schedule> page=new Page<>(1,5);
        IPage iPage=scheduleMapper.selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Schedule> findScheduleInSevenDays(Date dateFormat1) {
        return scheduleMapper.selectScheduleInSevenDays(dateFormat1);
    }

    @Override
    public ScheduleExecution addSchedule(Schedule schedule) {
        if(schedule == null){
            return new ScheduleExecution(false,"添加公告信息为空");
        }
        Date date=DateUtil.dateFormat1(new Date());
        schedule.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        try{
            int effectedNum = scheduleMapper.insert(schedule);
            if(effectedNum < 1){
                return new ScheduleExecution(false,"添加公告信息失败");
            }
        }catch (Exception e){
            throw new ScheduleOperationException(e.toString());
        }
        return new ScheduleExecution(true);
    }

    @Override
    public ScheduleExecution deleteSchedule(Integer id) {
        if(id == null){
            return new ScheduleExecution(false,"删除公告信息Id为空");
        }
        try{
            int effectedNum = scheduleMapper.deleteById(id);
            if(effectedNum < 1){
                return new ScheduleExecution(false,"删除公告信息失败");
            }
        }catch (Exception e){
            throw new ScheduleOperationException(e.toString());
        }
        return new ScheduleExecution(true);
    }

    @Override
    public ScheduleExecution upDateSchedule(Schedule schedule) {
        if(schedule == null){
            return new ScheduleExecution(false,"更新公告信息为空");
        }
        try{
            int effectedNum = scheduleMapper.updateById(schedule);
            if(effectedNum < 1){
                return new ScheduleExecution(false,"更新公告信息失败");
            }
        }catch (Exception e){
            throw new ScheduleOperationException(e.toString());
        }
        return new ScheduleExecution(true);
    }
}
