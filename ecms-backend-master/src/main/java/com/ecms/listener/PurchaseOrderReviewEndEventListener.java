package com.ecms.listener;

import com.ecms.bean.PurchaseOrdersInfo;
import com.ecms.bean.PurchaseOrdersListInfo;
import com.ecms.common.BusinessConstants;
import com.ecms.service.MaterialRepertoryService;
import com.ecms.service.PurchaseOrdersListService;
import com.ecms.service.PurchaseOrdersService;
import com.ecms.utils.ApplicationContextHandler;
import com.ecms.web.filter.LoginFilter;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@Component
public class PurchaseOrderReviewEndEventListener implements ExecutionListener {

    @Resource
    private PurchaseOrdersService purchaseOrdersService;

    @Resource
    private PurchaseOrdersListService purchaseOrdersListService;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        if(null==purchaseOrdersService){
            purchaseOrdersService = ApplicationContextHandler.getBean(PurchaseOrdersService.class);
        }
        if(null==purchaseOrdersListService){
            purchaseOrdersListService = ApplicationContextHandler.getBean(PurchaseOrdersListService.class);
        }
        String businessKey = delegateExecution.getProcessInstanceBusinessKey();
        String taskFlag = delegateExecution.getVariable("taskFlag").toString();
        PurchaseOrdersInfo purchaseOrdersInfo = new PurchaseOrdersInfo();
        purchaseOrdersInfo.setId(Integer.valueOf(businessKey));
        switch (taskFlag){
            case "taskRejected":
                purchaseOrdersInfo.setReviewStatus(BusinessConstants.AUDITING_NO_PASS);
                break;
            case "taskCompleted":
                purchaseOrdersService.updateMaterialRepertoryByPurchaseOrdersId(purchaseOrdersInfo.getId());
                purchaseOrdersInfo.setReviewStatus(BusinessConstants.AUDITING_PASS);
                PurchaseOrdersListInfo ordersListInfo = new PurchaseOrdersListInfo();
                ordersListInfo.setPurchaseOrdersId(Integer.valueOf(businessKey));
                ordersListInfo.setStatus(STATUS_DEFAULT);
                ordersListInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<PurchaseOrdersListInfo> ordersListInfoList = purchaseOrdersListService.getListBy(ordersListInfo);
                if (ordersListInfoList.size()!=0){
                    for (PurchaseOrdersListInfo info : ordersListInfoList){
                        info.setReceivedStatus(BusinessConstants.AUDITING_PASS);
                        purchaseOrdersListService.updateByPrimaryKeySelective(info);
                    }
                }
                break;
        }
        purchaseOrdersService.updateByPrimaryKeySelective(purchaseOrdersInfo);
    }

}
