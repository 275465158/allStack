package com.ecms.service;


import com.ecms.bean.BpmDefinitionInfo;
import com.ecms.web.filter.LoginFilter;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ecms.common.BusinessConstants.DEFAULT_PAGE_SIZE;

@Service
public class ActivitiWorkflowService extends ActivitiService{

    @Resource
    private UserService userService;
    @Resource
    private BpmDefinitionService bpmDefinitionService;
    /**
     * 启动流程
     * @param wfKey
     * @param bussinessKey
     * @param variables
     */
    public void startWorkflow(String wfKey, String bussinessKey, Map<String, Object> variables) {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance pi = null;
        if (variables.size() > 0) {
            pi = runtimeService.startProcessInstanceByKey(wfKey, bussinessKey, variables);
        } else {
            pi = runtimeService.startProcessInstanceByKey(wfKey, bussinessKey);
        }
        BpmDefinitionInfo bpmDefinitionInfo = bpmDefinitionService.getDefinitionByWfKey(wfKey);
        String taskName = bpmDefinitionInfo.getTaskNameRules();
        String[] taskNameRules = taskName.split("-");
        for(String nameRule : taskNameRules){
            String replaceStr = nameRule.substring(nameRule.indexOf(":")+1);
            if(null!=variables.get(replaceStr))
            taskName = taskName.replace(replaceStr, variables.get(replaceStr).toString());
        }
        runtimeService.setProcessInstanceName(pi.getId(),taskName);

    }

    /**
     * 退回流程
     *
     * @param taskId     当前任务ID
     * @param activityId 退回节点ID
     * @param variables  流程存储参数
     * @throws Exception
     */

    public void backProcess(String taskId, String activityId,
                            Map<String, Object> variables) throws Exception {
        if (org.apache.commons.lang.StringUtils.isEmpty(activityId)) {
            throw new Exception("退回目标节点ID为空！");
        }
        // 查找所有并行任务节点，同时驳回
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
                taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
        for (Task task : taskList) {
            commitProcess(task.getId(), variables, activityId);
        }
    }


    /**
     * 取回流程
     *
     * @param taskId     当前任务ID
     * @param activityId 取回节点ID
     * @throws Exception
     */

    public void callBackProcess(String taskId, String activityId)
            throws Exception {
        if (StringUtils.isEmpty(activityId)) {
            throw new Exception("目标节点ID为空！");
        }
        // 查找所有并行任务节点，同时取回
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
                taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
        for (Task task : taskList) {
            commitProcess(task.getId(), null, activityId);
        }
    }

    /**
     * 中止流程(特权人直接审批通过等)
     *
     * @param taskId
     */

    public void endProcess(String taskId, Map<String, Object> vars) throws Exception {
        FlowNode endActivity = findActivitiImpl(taskId, "end");
        commitProcess(taskId, vars, endActivity.getId());
    }


    /**
     * 根据当前任务ID，查询可以驳回的任务节点
     *
     * @param taskId 当前任务ID
     */

//    public List<FlowNode> findBackAvtivity(String taskId) throws Exception {
//        List<FlowNode> rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId,
//                null), new ArrayList<FlowNode>(),
//                new ArrayList<FlowNode>());
//        return reverList(rtnList);
//    }

    /**
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点
     *
     * @param taskId       当前任务ID
     * @param currActivity 当前活动节点
     * @param rtnList      存储回退节点集合
     * @param tempList     临时存储节点集合（存储一次迭代过程中的同级userTask节点）
     * @return 回退节点集合
     */
//
//    private List<FlowNode> iteratorBackActivity(String taskId,
//                                                       FlowNode currActivity, List<FlowNode> rtnList,
//                                                           List<FlowNode> tempList) throws Exception {
//        // 查询流程定义，生成流程树结构
//        ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
//        // 当前节点的流入来源
//        List<SequenceFlow> incomingTransitions = currActivity.getIncomingFlows();
//        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
//        List<FlowNode> exclusiveGateways = new ArrayList<FlowNode>();
//        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
//        List<FlowNode> parallelGateways = new ArrayList<FlowNode>();
//        // 遍历当前节点所有流入路径
//        for (SequenceFlow pvmTransition : incomingTransitions) {
////            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
//            FlowNode activityImpl = (FlowNode)pvmTransition.getSourceFlowElement();
////            String type = (String) activityImpl.getProperty("type");
//            if (activityImpl.getClass().equals(ParallelGateway.class)) {
//                parallelGateways.add(activityImpl);
//            }else if(activityImpl.getClass().equals(StartEvent.class)){
//                return rtnList;
//            }else if(activityImpl.getClass().equals(UserTask.class)){
//                tempList.add(activityImpl);
//            }else if(activityImpl.getClass().equals(ExclusiveGateway.class)) {
//                currActivity = (FlowNode) pvmTransition.getSourceFlowElement();
//                exclusiveGateways.add(currActivity);
//            }
//        }
//        /**
//         * 迭代条件分支集合，查询对应的userTask节点
//         */
//        for (FlowNode activityImpl : exclusiveGateways) {
//            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
//        }
//        /**
//         * 迭代并行集合，查询对应的userTask节点
//         */
//        for (FlowNode activityImpl : parallelGateways) {
//            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
//        }
//        /**
//         * 根据同级userTask集合，过滤最近发生的节点
//         */
//        currActivity = filterNewestActivity(processInstance, tempList);
//        if (currActivity != null) {
//            // 查询当前节点的流向是否为并行终点，并获取并行起点ID
//            String id = findParallelGatewayId(currActivity);
//            if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
//                rtnList.add(currActivity);
//            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
//                currActivity = findActivitiImpl(taskId, id);
//            }
//            // 清空本次迭代临时集合
//            tempList.clear();
//            // 执行下次迭代
//            iteratorBackActivity(taskId, currActivity, rtnList, tempList);
//        }
//        return rtnList;
//    }

    /**
     * 根据任务id获取对应流程实例
     * @param taskId 任务id
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception{
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(
                findTaskById(taskId).getProcessInstanceId()
        ).singleResult();
        if(processInstance== null){
            throw new Exception("未找到流程实例!");
        }
        return processInstance;
    }

    /**
     * 按照部署id查找流程实例
     * @param deployId
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByDeploymentId(String deployId) throws  Exception{
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().deploymentId(deployId).singleResult();
        if(processInstance == null){
            throw  new Exception("未能找到流程实例!");
        }
        return processInstance;
    }

    /**
     * 根据任务id获取任务实例
     * @param taskId 任务id
     * @return
     * @throws Exception
     */
    public TaskEntity findTaskById(String taskId) throws Exception{
        TaskService taskService = processEngine.getTaskService();
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task==null){
            throw new Exception("未找到任务实例!");
        }
        return task;
    }

    /**
     * 根据任务id获取历史任务实例
     * @param taskId
     * @return
     * @throws Exception
     */
    public HistoricTaskInstanceEntity findHistoricTaskById(String taskId) throws Exception{
        HistoricTaskInstanceEntity
                task = (HistoricTaskInstanceEntity) processEngine.getHistoryService().createHistoricTaskInstanceQuery()
                .taskId(taskId).singleResult();
        if(task == null){
            throw new Exception("未找到历史任务");
        }
        return task;
    }


    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     *
     * @param processInstanceId
     * @param key
     * @return
     */

    private List<Task> findTaskListByKey(String processInstanceId, String key) {
        TaskService taskService = processEngine.getTaskService();
        return taskService.createTaskQuery().processInstanceId(
                processInstanceId).taskDefinitionKey(key).list();
    }

    /**
     * 转办流程
     *
     * @param taskId   当前任务节点ID
     * @param userCode 被转办人Code
     */

    public void transferAssignee(String taskId, String userCode) {
        TaskService taskService = processEngine.getTaskService();
        taskService.setAssignee(taskId, userCode);
    }
    /**
     * @param taskId     当前任务ID
     * @param variables  流程变量
     * @param activityId 流程转向执行任务节点ID<br>
     *                   <p>
     *                   此参数为空，默认为提交操作
     * @throws Exception
     */

    private void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        TaskService taskService = processEngine.getTaskService();
        // 跳转节点为空，默认提交操作
        if (org.apache.commons.lang.StringUtils.isEmpty(activityId)) {
            if(variables.containsKey("taskComment")){
                taskService.addComment(taskId,null,variables.get("taskComment").toString());
            }
            taskService.complete(taskId, variables);
        }
        else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
    }


    public BpmnModel findModelByDeployId(String deploymentId) throws Exception{
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(pd.getId());

        InputStream xmlIs = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        BpmnModel bm = new BpmnXMLConverter().convertToBpmnModel(new InputStreamSource(xmlIs), false, true);
        if(bm==null){
            throw new Exception("未找到流程模型");
        }
        return bm;
    }

    /**
     * 流程转向操作
     *
     * @param taskId     当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables  流程变量
     * @throws Exception
     */

    private void turnTransition(String taskId, String activityId,
                                Map<String, Object> variables) throws Exception {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task==null) {
            throw new Exception("流程未启动或已执行完成，无法撤回");
        }
        String userid = String.valueOf(LoginFilter.getCurrentUser().getId());
        List<HistoricTaskInstance> htiList = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .orderByTaskCreateTime()
                .asc()
                .list();
        String myTaskId = null;
        HistoricTaskInstance myTask = null;
        for(HistoricTaskInstance hti : htiList) {
            if(userid.equals(hti.getAssignee())) {
                myTaskId = hti.getId();
                myTask = hti;
                break;
            }
        }
        if(null==myTaskId) {
            throw new Exception("该任务非当前用户提交，无法撤回");
        }

        String processDefinitionId = myTask.getProcessDefinitionId();
        //ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
        FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);


        Execution execution = processEngine.getRuntimeService().createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String currActivityId = execution.getActivityId();
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currActivityId);

        //记录原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        oriSequenceFlows.addAll(flowNode.getOutgoingFlows());

        //清理活动方向
        flowNode.getOutgoingFlows().clear();
        //建立新方向
        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(flowNode);
        newSequenceFlow.setTargetFlowElement(myFlowNode);
        newSequenceFlowList.add(newSequenceFlow);
        flowNode.setOutgoingFlows(newSequenceFlowList);


        taskService.addComment(task.getId(), task.getProcessInstanceId(), "撤回");

        //完成任务
        taskService.complete(task.getId(),variables);
        //恢复原方向
        flowNode.setOutgoingFlows(oriSequenceFlows);
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */

    private void restoreTransition(FlowNode activityImpl,
                                   List<SequenceFlow> oriPvmTransitionList) {
        // 清空现有流向
        activityImpl
                .getOutgoingFlows().clear();
        // 还原以前流向
        activityImpl.setOutgoingFlows(oriPvmTransitionList);
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     *
     * @param taskId     任务ID
     * @param activityId 活动节点ID <br>
     *                   <p>
     *                   如果为null或""，则默认查询当前活动节点 <br>
     *                   <p>
     *                   如果为"end"，则查询结束节点 <br>
     * @return
     * @throws Exception
     */

    private FlowNode findActivitiImpl(String taskId, String activityId)
            throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }
        BpmnModel bm = findModelByDeployId(processDefinition.getDeploymentId());
        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            Collection<FlowElement> elementList = bm.getMainProcess().getFlowElements();
            for (FlowElement flowElement : elementList) {
                if (flowElement.getClass().equals(EndEvent.class)) {
                    return (FlowNode)flowElement;
                }
            }
        }
        // 根据节点ID，获取对应的活动节点
        FlowNode activityImpl = (FlowNode)bm.getMainProcess().getFlowElement(activityId);
        return activityImpl;
    }

    public List<Map<String, String>> getAssignees(String wfKey) throws Exception {
        List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
        List<Map<String, String>> assingeeList = new ArrayList<Map<String, String>>();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //根据部署id获取定义实体
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(wfKey).singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(pd.getId());

        InputStream xmlIs = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        BpmnModel bm = new BpmnXMLConverter().convertToBpmnModel(new InputStreamSource(xmlIs), false, true);
        Collection<FlowElement> elementList = bm.getMainProcess().getFlowElements();
        for (FlowElement flowElement : elementList) {
            if (flowElement.getClass().equals(UserTask.class)) {
                UserTask currentTask = (UserTask) flowElement;
                Map<String, String> flowMap = new HashMap<String, String>();
                flowMap.put("TaskName", currentTask.getName());
                flowMap.put("Assignee", currentTask.getAssignee());
                assingeeList.add(flowMap);
            }
        }
        return assingeeList;
    }
    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */

    private List<SequenceFlow> clearTransition(FlowNode activityImpl) {
        // 存储当前节点所有流向临时变量
        List<SequenceFlow> oriPvmTransitionList = new ArrayList<SequenceFlow>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        for (SequenceFlow pvmTransition : activityImpl
                .getOutgoingFlows()) {
            oriPvmTransitionList.add(pvmTransition);
        }
        activityImpl.getOutgoingFlows().clear();
        return oriPvmTransitionList;
    }

    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */

    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {

        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());
        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }
        return processDefinition;
    }


    /**
     * 查询任务 - 按照派发接受人id
     * @param assignee 任务接受人id
     * @param pageNum
     * @return
     */
    public List<Map<String, Object>> queryTask(String assignee, int pageNum) {
        List<Map<String, Object>> rtnList = new ArrayList<>();

        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //办理人的任务列表
        List<Task> list = taskQuery
                //指定办理人
                .taskCandidateOrAssigned(assignee)
                .orderByTaskCreateTime().desc()
                .listPage((pageNum-1)*DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE);
        //遍历任务列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String, Object> map = new HashMap<>();
                map.put("assignee", task.getAssignee());
                map.put("jobName", task.getName());
                map.put("created", df.format(task.getCreateTime()));
                map.put("status", task.getDelegationState());
                map.put("id", task.getId());
                map.put("category", task.getCategory());
                map.put("formKey", task.getFormKey());
                ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                map.put("businessKey", pi.getBusinessKey());
                map.put("processInstanceName", pi.getName());
                Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentId(pi.getDeploymentId()).singleResult();
                map.put("deployName", deployment.getName());
                rtnList.add(map);
            }
        }
        return rtnList;
    }

    /**
     * 查询任务 - 按照派发接受人id，任务名字模糊查询
     * @param assignee 任务接受人id
     * @param pageNum
     * @return
     */
    public List<Map<String, Object>> queryTask(String assignee, String taskName, int pageNum) {
        List<Map<String, Object>> rtnList = new ArrayList<>();

        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //办理人的任务列表
        if(StringUtils.isNotEmpty(taskName)){
            taskQuery.taskNameLike("%"+taskName+"%");
        }
        List<Task> list = taskQuery
                .orderByTaskCreateTime().desc()
                .taskAssignee(assignee)//指定办理人
                .listPage((pageNum-1)*DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE);
        //遍历任务列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String, Object> map = new HashMap<>();
                map.put("assignee", task.getAssignee());
                map.put("jobName", task.getName());
                map.put("created", df.format(task.getCreateTime()));
                map.put("status", task.getDelegationState());
                map.put("id", task.getId());
                map.put("category", task.getCategory());
                map.put("formKey", task.getFormKey());
                ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                map.put("businessKey", pi.getBusinessKey());
                map.put("processInstanceName", pi.getName());
                Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentId(pi.getDeploymentId()).singleResult();
                map.put("deployName", deployment.getName());
                rtnList.add(map);
            }
        }
        return rtnList;
    }

    public List<Map<String, Object>> queryHistory(String assignee, String taskName, Integer pageNum){
        List<Map<String, Object>> rtnList = new ArrayList<>();
        HistoryService historyService = processEngine.getHistoryService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        HistoricTaskInstanceQuery queryBuilder = historyService.createHistoricTaskInstanceQuery();
        queryBuilder.finished();
        if(StringUtils.isNotEmpty(taskName)){
            queryBuilder.taskNameLike("%"+taskName+"%");
        }
        queryBuilder.taskAssignee(assignee);
        queryBuilder.orderByTaskCreateTime().desc();
        //listPage第一个参数为开始索引，第二个参数为分页数量
        List<HistoricTaskInstance> htis = queryBuilder.listPage(DEFAULT_PAGE_SIZE*(pageNum-1), DEFAULT_PAGE_SIZE);
        for(HistoricTaskInstance instance : htis){
            Map<String, Object> m = new HashMap<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            m.put("id", instance.getId());
            m.put("assignee", instance.getAssignee());
            m.put("jobName", instance.getName());
            m.put("startTime", df.format(instance.getStartTime()));
            m.put("endTime", df.format(instance.getEndTime()));
            m.put("formKey", instance.getFormKey());
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(instance.getProcessInstanceId())
                    .singleResult();
            m.put("businessKey", hpi.getBusinessKey());
            m.put("processInstanceName", hpi.getName());
            Deployment depolyment =  repositoryService.createDeploymentQuery().deploymentId(hpi.getDeploymentId()).singleResult();
            m.put("deployName", depolyment.getName());
            rtnList.add(m);
        }
        return rtnList;
    }

    /**
     * 执行任务
     * @param wfKey
     * @param bussinessKey
     */
    @Deprecated
    public void compileTask(String wfKey, String bussinessKey) {
        //ProcessEngine processEngine = createActivitiEngine();
//        Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().processDefinitionKey(wfKey).singleResult();
//        BPMVariableInfo searchInfo = new BPMVariableInfo();
//        searchInfo.setDeployId(deployment.getId());
//        List<BPMVariableInfo> list = variableService.getListBy(searchInfo);
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance pi = null;
//        if (list.size() > 0) {
//            Map<String, Object> vars = new HashMap<>();
//            JSONArray jsonArray = JSONArray.parseArray(list.get(0).getValJson());
//            Iterator iterator = jsonArray.iterator();
//            while (iterator.hasNext()) {
//                JSONObject obj = (JSONObject) iterator.next();
//                vars.put(obj.getString("varKey"), obj.get("varVal"));
//            }
//
//        } else {
//
//        }
    }

    /**
     * 执行当前步骤
     * @param taskId
     * @param variables
     */
    public void compileTask(String taskId, Map<String, Object> variables) {
        if(variables.size()>0) {
            if(variables.containsKey("taskComment")){
                Authentication.setAuthenticatedUserId(LoginFilter.getCurrentUser().getId().toString());
                processEngine.getTaskService().addComment(taskId,null,variables.get("taskComment").toString());
            }
            processEngine.getTaskService().setVariables(taskId, variables);
        }

        //taskId：任务id
        processEngine.getTaskService().complete(taskId);
    }

    /**
     * 当前正在运行的任务的备注列表
     * @param taskId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProcessComments(String taskId) throws Exception {
        List<Map<String, Object>> rtnList = new ArrayList<>();
//         1) 获取流程实例的ID
        TaskEntity task = findTaskById(taskId);
        ProcessInstance pi = processEngine
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
//       2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = processEngine
                .getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(pi.getId())
                .activityType("userTask")
                .list();
//       3）查询每个历史任务的批注
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = processEngine.getTaskService().getTaskComments(historytaskId);
//             4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                for(Comment comment : comments){
                    Map<String, Object> c = new HashMap<>();
                    c.put("userId", comment.getUserId());
                    c.put("author", userService.getById(Integer.valueOf(comment.getUserId())).getUserName());
                    c.put("createTime", comment.getTime());
                    c.put("comment", comment.getFullMessage());
                    rtnList.add(c);
                }
            }
        }
//        5）返回
        return rtnList;
    }

    /**
     * 历史任务的备注列表
     * @param taskId
     * @return
     */
    public List<Map<String, Object>> getHistoricComments(String taskId) throws Exception{
        List<Map<String, Object>> rtnList = new ArrayList<>();
        HistoricTaskInstanceEntity taskInstanceEntity = findHistoricTaskById(taskId);
        HistoricProcessInstance hpi = processEngine
                .getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(taskInstanceEntity.getProcessInstanceId())
                .singleResult();
        List<HistoricActivityInstance> hais = processEngine
                .getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(hpi.getId())
                .activityType("userTask")
                .list();
        for(HistoricActivityInstance hai : hais){
            String historicTaskId = hai.getTaskId();
            List<Comment> comments = processEngine
                    .getTaskService()
                    .getTaskComments(historicTaskId);
            if(comments != null && comments.size()>0){
                for(Comment comment : comments) {
                    Map<String, Object> c = new HashMap<>();
                    c.put("userId", comment.getUserId());
                    c.put("author", userService.getById(Integer.valueOf(comment.getUserId())).getUserName());
                    c.put("createTime", comment.getTime());
                    c.put("comment", comment.getFullMessage());
                    rtnList.add(c);
                }
            }
        }
        return rtnList;
    }

}
