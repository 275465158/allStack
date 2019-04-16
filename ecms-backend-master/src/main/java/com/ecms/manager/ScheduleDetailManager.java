package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ScheduleDetailEntity;
import com.ecms.dal.mapper.ScheduleDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ScheduleDetailManager extends BaseManager<ScheduleDetailEntity> {

	@Resource
	private ScheduleDetailMapper scheduleDetailMapper;

    @Override
    protected BasicMapper<ScheduleDetailEntity> getMapper() {
        return scheduleDetailMapper;
    }

    public List<Map<String,Object>> getScheduleList(ScheduleDetailEntity scheduleDetailEntity,String scheduleYear) {
        return scheduleDetailMapper.getScheduleList(scheduleDetailEntity,scheduleYear);
    }

    public List<Map<String,Object>> getSelectionList(ScheduleDetailEntity scheduleDetailEntity) {
        return scheduleDetailMapper.getSelectionList(scheduleDetailEntity);
    }

    public void deleteByScheduleDate(String scheduleDate){
        scheduleDetailMapper.deleteByScheduleDate(scheduleDate);
    }
}
