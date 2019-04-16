package com.ecms.service;

import com.ecms.bean.ProduceMaterialReturnLogInfo;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.dal.entity.ProduceMaterialReturnLogEntity;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

/**
 * @author Richie
 * @version 2019/1/9 14:00
 */
@Service
public class ProduceMaterialReturnLogService extends BaseService<ProduceMaterialReturnLogEntity,ProduceMaterialReturnLogInfo> {

    @Resource
    private ProduceMaterialReturnLogManager produceMaterialReturnLogManager;

    @Resource
    private ProduceMaterialReturnLogService produceMaterialReturnLogService;

    @Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private ProductionPlanManager productionPlanManager;

    @Override
    protected BaseManager<ProduceMaterialReturnLogEntity> getManager() {
        return produceMaterialReturnLogManager;
    }

    @Transactional
    public void returnMaterial(ProduceMaterialReturnLogInfo produceMaterialReturnLogInfo){
        //插入到退料记录表
        produceMaterialReturnLogInfo.setId(null);
        ProduceMaterialReturnLogEntity produceMaterialReturnLogEntity = ExchangeUtils.exchangeObject(produceMaterialReturnLogInfo, ProduceMaterialReturnLogEntity.class);
        produceMaterialReturnLogEntity.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        produceMaterialReturnLogEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        produceMaterialReturnLogEntity.setReturnDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
        produceMaterialReturnLogEntity.setIssuanceNumber(produceMaterialReturnLogInfo.getIssuanceNumber());
        Integer returnNumber = produceMaterialReturnLogService.getNextNumber();
        produceMaterialReturnLogEntity.setReturnNo(returnNumber);
        produceMaterialReturnLogEntity.setReturnNumber(frontCompWithZore(returnNumber, 8));
        produceMaterialReturnLogManager.insertBy(produceMaterialReturnLogEntity);

        //更新仓库库存
        MaterialRepertoryDetailEntity mrde = materialRepertoryDetailManager.getEntityById(produceMaterialReturnLogInfo.getMaterialRepertoryDetailId());
        mrde.setInventoryQty(mrde.getInventoryQty()+produceMaterialReturnLogInfo.getReturnQty());
        materialRepertoryDetailManager.updateByPrimaryKeySelective(mrde);

        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
        mre.setMaterialId(produceMaterialReturnLogInfo.getMaterialId());
        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
        if(mreList.size() != 0){
            mre = mreList.get(0);
            mre.setInventoryQty(mre.getInventoryQty()+produceMaterialReturnLogInfo.getReturnQty());
            materialRepertoryManager.updateByPrimaryKeySelective(mre);
        }
        //更新生产计划已领数量
        ProductionPlanEntity productionPlanEntity = productionPlanManager.getEntityById(produceMaterialReturnLogInfo.getPlanId());
        productionPlanEntity.setIssueQty(productionPlanEntity.getIssueQty()-produceMaterialReturnLogInfo.getReturnQty());
        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
    }

    public Float getReturnedQty(Integer materialId, String productionNumber, Integer companyId){
        return produceMaterialReturnLogManager.getReturnedQty(materialId,productionNumber,companyId);
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "no_return_";
    }
}
