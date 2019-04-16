package com.ecms.manager;

import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.dal.mapper.ProductionPlanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ProductionPlanManager extends BaseManager<ProductionPlanEntity> {

	@Resource
	private ProductionPlanMapper productionPlanMapper;

    @Override
    protected BasicMapper<ProductionPlanEntity> getMapper() {
        return productionPlanMapper;
    }

    public List<ProductionPlanInfo> getProductionBatchList(int materialId){
        return productionPlanMapper.getProductionBatchList(materialId);
    }

    public List<ProductionPlanInfo> getProductionNumberList(Integer materialId,String contractId){
        return productionPlanMapper.getProductionNumberList(materialId,contractId);
    }

    public void deleteByOrderCode(String orderCode){
        productionPlanMapper.deleteByOrderCode(orderCode);
    }

    public Integer getSellerOrderLogSize(Integer orderId){
        return productionPlanMapper.getSellerOrderLogSize(orderId);
    }

    public Integer getProductionPlanSize(String orderCode){
        return productionPlanMapper.getProductionPlanSize(orderCode);
    }

    public void updatePlanFlagByOrderId(Integer orderId){
        productionPlanMapper.updatePlanFlagByOrderId(orderId);
    }

    public void deleteProductionPlan(String orderCode){
        productionPlanMapper.deleteProductionPlan(orderCode);
    }

}
