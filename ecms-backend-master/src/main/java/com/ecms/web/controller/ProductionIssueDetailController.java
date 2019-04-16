package com.ecms.web.controller;

import com.ecms.bean.ProduceMaterialReturnLogInfo;
import com.ecms.bean.ProductionIssueDetailInfo;
import com.ecms.bean.ProductionIssueInfo;
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
@RequestMapping("/productionIssueDetail")
public class ProductionIssueDetailController extends BaseController<ProductionIssueDetailEntity,ProductionIssueDetailInfo> {

    @Resource
    private ProductionIssueDetailService productionIssueDetailService;

    @Resource
    private ProductionIssueService productionIssueService;

    @Resource
    private RepertoryService repertoryService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProduceMaterialReturnLogService produceMaterialReturnLogService;

    @Override
    protected BaseService<ProductionIssueDetailEntity, ProductionIssueDetailInfo> getBaseService() {
        return productionIssueDetailService;
    }

    @RequestMapping("getIssueList")
    public Result<List<ProductionIssueDetailInfo>> getIssueList(ProductionIssueDetailInfo productionIssueDetailInfo) {
        productionIssueDetailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        productionIssueDetailInfo.setStatus(STATUS_DEFAULT);
        ExchangeUtils.convertBlank2Null(productionIssueDetailInfo);
        List<ProductionIssueDetailInfo> piiList = productionIssueDetailService.getListBy(productionIssueDetailInfo);
        ProductionIssueInfo productionIssueInfo = new ProductionIssueInfo();
        productionIssueInfo.setProductionNumber(productionIssueDetailInfo.getProductionNumber());
        productionIssueInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        productionIssueInfo.setStatus(STATUS_DEFAULT);
        List<ProductionIssueInfo> pidList = productionIssueService.getListBy(productionIssueInfo);
        if(pidList.size() != 0){
            productionIssueInfo = pidList.get(0);
        }
        Float usedQty = productionIssueInfo.getUsedQty();
        if(piiList.size() != 0){
            for(ProductionIssueDetailInfo pii : piiList){
                Float issueQty = pii.getIssueQty();
                if(pii.getRepertoryId() != null){
                    RepertoryEntity re = repertoryService.getEntity(pii.getRepertoryId());
                    pii.setRepertoryName(re.getRepertoryName());
                }
                if(pii.getOperatingPersonnelId() != null){
                    EmployeeEntity ee = employeeService.getEntity(pii.getOperatingPersonnelId());
                    pii.setOperatingPersonnelName(ee.getName());
                }
                if(pii.getMaterialId() != null){
                    MaterialBomEntity mbe = materialBomService.getEntity(pii.getMaterialId());
                    pii.setMaterialCode(mbe.getMaterialCode());
                }
                if(pii.getIssuanceNumber() != null){
                    ProduceMaterialReturnLogInfo pmrli = new ProduceMaterialReturnLogInfo();
                    pmrli.setIssuanceNumber(pii.getIssuanceNumber());
                    List<ProduceMaterialReturnLogInfo> pmrliList = produceMaterialReturnLogService.getListBy(pmrli);
                    if(pmrliList.size() != 0){
                        Float returnQty = 0f;
                        for(ProduceMaterialReturnLogInfo pmrlInfo : pmrliList){
                            returnQty += pmrlInfo.getReturnQty();
                        }
                        pii.setReturndQty(returnQty);
                    }
                }

                //可退料数量，根据已使用数量遍历
                if(pii.getIssueQty() >= usedQty){
                    pii.setIsReturnQty(pii.getIssueQty() - usedQty);
                    usedQty = 0f;
                }else{
                    usedQty = usedQty-pii.getIssueQty();
                    pii.setIsReturnQty(0f);
                }
            }
        }
        return ResultUtils.success(piiList);
    }
}
