package com.ecms.service;

import com.ecms.bean.BpmDefinitionInfo;
import com.ecms.dal.entity.BpmDefinitionEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.BpmDefinitionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BpmDefinitionService extends BaseService<BpmDefinitionEntity, BpmDefinitionInfo> {
    @Resource
    private BpmDefinitionManager manager;

    @Override
    protected BaseManager<BpmDefinitionEntity> getManager() {
        return manager;
    }

    public BpmDefinitionInfo getDefinitionByModelId(Integer modelId) {

        BpmDefinitionInfo searchInfo = new BpmDefinitionInfo(), rtnInfo = null;
        searchInfo.setModelId(modelId);
        List<BpmDefinitionInfo> existList = getListBy(searchInfo);
        if (existList.size() > 0) {
            rtnInfo = existList.get(0);
        }
        return rtnInfo;
    }

    public BpmDefinitionInfo getDefinitionByDeployId(Integer deployId) {
        BpmDefinitionInfo searchInfo = new BpmDefinitionInfo(), rtnInfo = null;
        searchInfo.setDeployId(deployId);
        List<BpmDefinitionInfo> existList = getListBy(searchInfo);
        if (existList.size() > 0) {
            rtnInfo = existList.get(0);
        }
        return rtnInfo;
    }

    public BpmDefinitionInfo getDefinitionByWfKey(String workflowKey) {
        BpmDefinitionInfo searchInfo = new BpmDefinitionInfo(), rtnInfo = null;
        searchInfo.setDefKey(workflowKey);
        List<BpmDefinitionInfo> existList = getListBy(searchInfo);
        if (existList.size() > 0) {
            rtnInfo = existList.get(0);
        }
        return rtnInfo;
    }

    public void delByModelId(String modelId) {
        BpmDefinitionInfo delBpm = getDefinitionByModelId(Integer.valueOf(modelId));
        if (null != delBpm) {
            this.deleteBy(delBpm.getId());
        }
    }
}
