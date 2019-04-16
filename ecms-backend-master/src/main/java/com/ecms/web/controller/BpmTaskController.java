package com.ecms.web.controller;


import com.ecms.bean.UserInfo;
import com.ecms.common.Result;
import com.ecms.service.ActivitiModelService;
import com.ecms.service.ActivitiWorkflowService;

import com.ecms.service.UserService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.activiti.engine.repository.Deployment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("activiti")

public class BpmTaskController {

    @Resource
    private ActivitiModelService modelService;

    @Resource
    private ActivitiWorkflowService workflowService;

    @Resource
    private UserService userService;
    @Deprecated
    @RequestMapping("deploy")
    public Result<Void> deploy(String bpmFileUrl, String deployName, String categoryName){
        modelService.deploy(bpmFileUrl, deployName, categoryName);
        return ResultUtils.success();
    }

    @RequestMapping("deployList")
    public Result<List<HashMap<String, Object>>> deployList(){
        List<Deployment> list = modelService.deployList();
        List<HashMap<String, Object>> deployList = new ArrayList<HashMap<String, Object>>();
        for(Deployment deployment : list){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("deployName", deployment.getName());
            map.put("categoryName", deployment.getCategory());
            map.put("id", deployment.getId());
            deployList.add(map);
        }
        return ResultUtils.success(deployList);
    }
    @Deprecated
    @RequestMapping("removeDeployment")
    public Result<Void> removeDeployment(String deployId){
        modelService.removeDeploy(deployId);
        return ResultUtils.success();
    }

    /**
     * 当前正在执行任务列表
     * @param pageNum
     * @return
     */
    @RequestMapping("queryTask")
    public Result<List<Map<String, Object>>> queryTask(int pageNum){
        UserInfo user = LoginFilter.getCurrentUser();
        String assignee = String.valueOf(user.getId());
        return ResultUtils.success(workflowService.queryTask(assignee, pageNum));
    }

    /**
     * 当前正在执行的任务查询方法
     * @param pageNum
     * @param taskName
     * @return
     */
    @RequestMapping("searchTask")
    public Result<List<Map<String, Object>>> searchTask(int pageNum, String taskName){
        UserInfo user = LoginFilter.getCurrentUser();
        String assignee = String.valueOf(user.getId());
        return ResultUtils.success(workflowService.queryTask(assignee, taskName, pageNum));
    }

    /**
     * 按照taskId遍历获取历史步骤中所有的任务备注信息
     * @param taskId
     * @return
     * @throws Exception
     */
    @RequestMapping("queryTaskLogs")
    public Result<List<Map<String, Object>>> queryTaskLogs(String taskId) throws Exception {
        return ResultUtils.success(workflowService.getProcessComments(taskId));
    }

    @RequestMapping("queryTaskHistoryLogs")
    public Result<List<Map<String, Object>>> queryTaskHistoryLogs(String taskId) throws Exception{
        return ResultUtils.success(workflowService.getHistoricComments(taskId));
    }

    /**
     * 历史任务记录列表
     * @param pageNum
     * @return
     */
    @RequestMapping("queryHistory")
    public Result<List<Map<String, Object>>> queryHistory(int pageNum){
        UserInfo user = LoginFilter.getCurrentUser();
        String assignee = String.valueOf(user.getId());
        return ResultUtils.success(workflowService.queryHistory(assignee, "", pageNum));
    }

    /**
     * 历史任务记录查询
     * @param pageNum
     * @param taskName
     * @return
     */
    @RequestMapping("searchHistory")
    public Result<List<Map<String, Object>>> searchHistory(int pageNum, String taskName){
        UserInfo user = LoginFilter.getCurrentUser();
        String assignee = String.valueOf(user.getId());
        return ResultUtils.success(workflowService.queryHistory(assignee, taskName, pageNum));
    }

}
