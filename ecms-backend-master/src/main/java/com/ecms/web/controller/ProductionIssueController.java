package com.ecms.web.controller;

import com.ecms.bean.ProduceMaterialReturnLogInfo;
import com.ecms.bean.ProductionIssueInfo;
import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.*;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

/**
 * @author Richie
 * @version 2018/9/16 17:00
 */
@RestController
@RequestMapping("/productionIssue")
public class ProductionIssueController extends BaseController<ProductionIssueEntity,ProductionIssueInfo> {

    @Resource
    private ProductionIssueService productionIssueService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProductionPlanService productionPlanService;

    @Resource
    private ProduceMaterialReturnLogService produceMaterialReturnLogService;

    @Override
    protected BaseService<ProductionIssueEntity, ProductionIssueInfo> getBaseService() {
        return productionIssueService;
    }

    /**
     * 获取发料数量
     * @param pageNum
     * @param pageSize
     * @param productionIssueInfo
     * @return
     */
    @RequestMapping("getProductionIssueList")
    public Result<Pager<ProductionIssueInfo>> getProductionIssueList(int pageNum, int pageSize, ProductionIssueInfo productionIssueInfo) {
        ExchangeUtils.convertBlank2Null(productionIssueInfo);
        Pager<ProductionIssueInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, productionIssueInfo, false);
        List<ProductionIssueInfo> piiList = pager.getList();
        if(piiList.size() != 0){
            for(ProductionIssueInfo pii : piiList){
                Integer materialId = pii.getMaterialId();
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);

                ProduceMaterialReturnLogInfo pmrli = new ProduceMaterialReturnLogInfo();
                pmrli.setMaterialId(materialId);
                pmrli.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                pmrli.setStatus(STATUS_DEFAULT);
                pmrli.setProductionNumber(pii.getProductionNumber());
                List<ProduceMaterialReturnLogInfo> pmrliList = produceMaterialReturnLogService.getListBy(pmrli);
                Float returnQty = 0f;
                if(pmrliList.size() != 0){
                    for(ProduceMaterialReturnLogInfo produceMaterialReturnLogInfo:pmrliList){
                        returnQty += produceMaterialReturnLogInfo.getReturnQty();
                    }
                }

                pii.setSurplusIssueQty(pii.getIssueQty()-pii.getUsedQty()-returnQty);
                ProductionPlanInfo ppi = new ProductionPlanInfo();
                pii.setStatus(STATUS_DEFAULT);
                pii.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                ppi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                ppi.setStatus(STATUS_DEFAULT);
                ppi.setProductionNumber(pii.getProductionNumber());
                List<ProductionPlanInfo> ppiList = productionPlanService.getListBy(ppi);
                if(ppiList.size() != 0){
                    ppi = ppiList.get(0);
                    pii.setOrderCode(ppi.getOrderCode());
                    pii.setContractId(ppi.getContractId());
                    pii.setSequenceNumber(ppi.getSequenceNumber());
                    pii.setProductionType(ppi.getProductionType());
                }
            }
        }
        return ResultUtils.success(pager);
    }
}
