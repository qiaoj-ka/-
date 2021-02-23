package com.cloth.mapper;

import com.cloth.entity.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {

    @Select("select * from schedule where TIMESTAMPDIFF(DAY,date,#{date}) <= time order by date DESC")
    List<Schedule> selectScheduleInSevenDays(@Param("date") Date date);

}
