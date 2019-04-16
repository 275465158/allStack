package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.IncomingInspectionEntity;
import com.ecms.dal.mapper.IncomingInspectionMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class IncomingInspectionManager extends BaseManager<IncomingInspectionEntity>{

    @Resource
    private IncomingInspectionMapper incomingInspectionMapper;

    @Override
    protected BasicMapper<IncomingInspectionEntity> getMapper() {
        return incomingInspectionMapper;
    }

    public IncomingInspectionEntity getIncomingByAccount(String account) {
        return incomingInspectionMapper.selectByAccount(account);
    }

    public List<IncomingInspectionEntity> getIncomingList(int pageNum, int pageSize, IncomingInspectionEntity incomingInspectionEntity) {
        PageHelper.startPage(pageNum, pageSize);
        return incomingInspectionMapper.select(incomingInspectionEntity);
    }

    public long getTotal(IncomingInspectionEntity incomingInspectionEntity) {
        return incomingInspectionMapper.selectCount(incomingInspectionEntity);
    }

    public void updateIncomingInspection(IncomingInspectionEntity incomingInspectionEntity) {
        incomingInspectionEntity.setId(3);
        incomingInspectionEntity.setMaterielName("333333333");
        incomingInspectionMapper.updateByPrimaryKeySelective(incomingInspectionEntity);
    }
}
