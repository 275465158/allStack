package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ScheduleDetailEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ScheduleDetailMapper extends BasicMapper<ScheduleDetailEntity> {
    @Select("SELECT SUM(work_hours) AS workHours,COUNT(*) AS workDays,LEFT(schedule_date,7) AS scheduleMonth" +
            ",schedule_id AS scheduleId FROM schedule_detail WHERE work_status = 1 AND schedule_year = #{scheduleYear} GROUP BY schedule_id")
    List<Map<String,Object>> getScheduleList(ScheduleDetailEntity scheduleDetailEntity,@Param("scheduleYear")String scheduleYear);

    @Select("SELECT schedule_year AS scheduleYear FROM schedule_detail GROUP BY schedule_year")
    List<Map<String,Object>> getSelectionList(ScheduleDetailEntity scheduleDetailEntity);

    @Delete("DELETE FROM schedule_detail WHERE schedule_date > #{scheduleDate}")
    void deleteByScheduleDate(@Param("scheduleDate")String scheduleDate);
}
