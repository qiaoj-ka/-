package com.cloth.service;

import com.cloth.entity.Schedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloth.exception.ScheduleExecution;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface IScheduleService extends IService<Schedule> {

    List<Schedule> findByCondition();

    List<Schedule> findScheduleInSevenDays(Date dateFormat1);

    ScheduleExecution addSchedule(Schedule schedule);

    ScheduleExecution deleteSchedule(Integer id);

    ScheduleExecution upDateSchedule(Schedule schedule);
}
