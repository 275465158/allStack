package com.ecms.service;

import com.ecms.bean.IncomingInspection;
import com.ecms.common.Pager;
import com.ecms.dal.entity.IncomingInspectionEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.IncomingInspectionManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IncomingInspectionService extends BaseService<IncomingInspectionEntity, IncomingInspection>  {

    @Resource
    private IncomingInspectionManager incomingInspectionManager;

    @Override
    protected BaseManager<IncomingInspectionEntity> getManager(){
        return incomingInspectionManager;
    }

    public Pager<IncomingInspection> getIncomingList(int pageNum, int pageSize, IncomingInspection incomingInspection) {
        IncomingInspectionEntity incomingInspectionEntity = ExchangeUtils.exchangeObject(incomingInspection, IncomingInspectionEntity.class);
        List<IncomingInspectionEntity> list = incomingInspectionManager.getIncomingList(pageNum, pageSize, incomingInspectionEntity);
        long total = incomingInspectionManager.getTotal(incomingInspectionEntity);
        List<IncomingInspection> incomingList = ExchangeUtils.exchangeList(list, IncomingInspection.class);
        Pager<IncomingInspection> pager = new Pager<IncomingInspection>(pageNum, pageSize, total, incomingList);
        return pager;
    }

    public void updateIncomingInspection(IncomingInspection incomingInspection) {
        IncomingInspectionEntity incomingInspectionEntity = ExchangeUtils.exchangeObject(incomingInspection, IncomingInspectionEntity.class);
        if(incomingInspection.getMaterielList().size() != 0){

        }
        incomingInspectionManager.updateIncomingInspection(incomingInspectionEntity);
    }
}
