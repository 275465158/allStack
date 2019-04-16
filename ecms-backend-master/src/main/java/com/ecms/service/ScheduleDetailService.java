package com.ecms.service;

import com.ecms.bean.ScheduleDetailInfo;
import com.ecms.dal.entity.ScheduleDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ScheduleDetailManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleDetailService extends BaseService<ScheduleDetailEntity, ScheduleDetailInfo> {

	@Resource
	private ScheduleDetailManager scheduleDetailManager;

    @Override
    protected BaseManager<ScheduleDetailEntity> getManager() {
        return scheduleDetailManager;
    }

    public List<Map<String,Object>> getScheduleList(ScheduleDetailInfo scheduleDetailInfo,String scheduleYear) {
        ScheduleDetailEntity scheduleDetailEntity = ExchangeUtils.exchangeObject(scheduleDetailInfo, ScheduleDetailEntity.class);
        List<Map<String,Object>> list = scheduleDetailManager.getScheduleList(scheduleDetailEntity,scheduleYear);
        return list;
    }

    public List<Map<String,Object>> getSelectionList(ScheduleDetailInfo scheduleDetailInfo) {
        ScheduleDetailEntity scheduleDetailEntity = ExchangeUtils.exchangeObject(scheduleDetailInfo, ScheduleDetailEntity.class);
        List<Map<String,Object>> list = scheduleDetailManager.getSelectionList(scheduleDetailEntity);
        return list;
    }

    public void updateScheduleDetail(ScheduleDetailInfo scheduleDetailInfo){
        List<Map<String,Object>> res = new ArrayList<>();
        res = scheduleDetailInfo.getDetailList();
        if(res.size() != 0){
            Integer id;
            Double workHours;
            Integer workStatus;
            for(int i=0;i<res.size();i++){
                ScheduleDetailEntity sde = new ScheduleDetailEntity();
                id = Integer.parseInt(res.get(i).get("id").toString());
                workHours = Double.valueOf(res.get(i).get("workHours").toString());
                workStatus = Integer.parseInt(res.get(i).get("workStatus").toString());
                sde.setId(id);
                sde.setWorkHours(workHours);
                sde.setWorkStatus(workStatus);
                scheduleDetailManager.updateByPrimaryKeySelective(sde);
            }
        }
    }
}
