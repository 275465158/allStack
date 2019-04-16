package com.ecms.listener;

import com.ecms.service.MaterialBomService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class MaterialBomReviewEndEventListener implements ExecutionListener {

    @Resource
    public MaterialBomService bomService;

    //需要静态属性
    public static MaterialBomService materialBomService;

    @PostConstruct
    public void init() {
        materialBomService = this.bomService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) {
        String businessKey = delegateExecution.getProcessInstanceBusinessKey();
        System.out.println(delegateExecution.getVariable("rejectFlag"));
        if(delegateExecution.getVariable("rejectFlag") == null){
            Integer materialId = Integer.parseInt(businessKey);
            materialBomService.auditingPass(materialId);
            System.out.println("记录id："+businessKey+"流程结束！");
        }
    }
}
