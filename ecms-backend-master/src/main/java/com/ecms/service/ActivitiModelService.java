package com.ecms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecms.bean.BpmDefinitionInfo;
import com.ecms.web.filter.LoginFilter;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.repository.*;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ecms.common.BusinessConstants.DEFAULT_PAGE_SIZE;

@Service
public class ActivitiModelService extends ActivitiService {

    @Resource
    private BpmDefinitionService bpmDefinitionService;
    /***
     * 流程图例中实体的创建方法组
     */

    /**
     *
     * @param id
     * @param name
     * @param assignee
     * @param formKey
     * @return
     */
    protected UserTask createUserTask(String id, String name, String assignee, String formKey) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
//        List<String> assigneeList = new ArrayList<>();
//        assigneeList.add(assignee);
//        userTask.setCandidateUsers(assigneeList);
        userTask.setAssignee(assignee);
        userTask.setFormKey(formKey);
        return userTask;
    }

    protected UserTask createMultiUserTask(String id, String name, List<String> assigneeList, String formKey) {
        UserTask userTask = new UserTask();
        MultiInstanceLoopCharacteristics miLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        miLoopCharacteristics.setSequential(false);
        String assigneeCommaSeparated = String.join(",", assigneeList);
        miLoopCharacteristics.setInputDataItem(assigneeCommaSeparated);
        miLoopCharacteristics.setElementVariable(assigneeCommaSeparated);
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateUsers(assigneeList);
        userTask.setFormKey(formKey);
        return userTask;
    }

    protected ExclusiveGateway createExclusive(String id, String name){
        ExclusiveGateway exGate = new ExclusiveGateway();
        exGate.setId(id);
        exGate.setName(name);
        return exGate;
    }

    protected SequenceFlow createSequenceFlow(String from, String to, String conditionExp) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        if (!org.apache.commons.lang.StringUtils.isEmpty(conditionExp)) {
            JSONArray conditionJSON = JSONArray.parseArray(conditionExp);
            StringBuilder finalStr = new StringBuilder();
            Map<String, List<Object>> condgrouping = conditionJSON.stream().collect(Collectors.groupingBy(e -> ((JSONObject) e).getString("conditionUnionId")));
            condgrouping.forEach((k, slist) -> {
                StringBuilder cStr = new StringBuilder();
                Stream.iterate(0, i -> i + 1).limit(slist.size()).forEach((c) -> {
                    JSONObject condObj = (JSONObject) slist.get(c);
                    String s = "${" + condObj.getString("conditionColumn") +
                            getMethod(condObj.getString("conditionMethod"), condObj.getString("conditionValue")) +
                            "}";
                    if(slist.size()>1 && slist.size()>c){
                        s += condObj.getString("conditionUnion");

                    }
                    cStr.append(s);
                });
                finalStr.append(cStr.toString());
            });
            System.out.println(finalStr.toString());
            flow.setConditionExpression(finalStr.toString());
        }
        return flow;
    }

    private String getMethod(String mStr, String mValue) {
        String rtn = "";
        switch (mStr) {
            case "eq":
                rtn = "==" + mValue;
                break;
            case "contain":
                rtn = "contain(" + mValue + ")";
                break;
            case "neq":
                rtn = "!=" + mValue;
                break;
            case "lte":
                rtn = "<=" + mValue;
                break;
            case "lt":
                rtn = "<" + mValue;
                break;
            case "gte":
                rtn = ">=" + mValue;
                break;
            case "gt":
                rtn = ">" + mValue;
                break;
        }
        return rtn;
    }

    private Map<String, Object> grouping(List<Map<String, Object>> list, String key) {
        // 使用TreeMap存放元素并排序
        Map<String, Object> newMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        list.stream().forEach(m -> {
            if (m.containsKey(key)) {
                List<Object> l = new ArrayList<>();
                l.add(m);
                newMap.put(m.get(key).toString(), l);
            }
        });
        return newMap;
    }

    protected StartEvent createStartEvent(String eventId) {
        StartEvent startEvent = new StartEvent();
        startEvent.setId(eventId);
        return startEvent;
    }

    protected EndEvent createEndEvent(String eventId, String formKey) {
        EndEvent endEvent = new EndEvent();
        endEvent.setId(eventId);
        ActivitiListener endEventListener = new ActivitiListener();
        endEventListener.setEvent("end");
        endEventListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
        endEventListener.setImplementation("com.ecms.listener."+formKey+"EndEventListener");
        List<ActivitiListener> listeners = new ArrayList<>();
        listeners.add(endEventListener);
        endEvent.setExecutionListeners(listeners);
        return endEvent;
    }

    /* 方法体结束 */
    //按照taskId获取deployId
    public String getDeployId(String taskId) {
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        return processDefinition.getDeploymentId();
    }
    /**
     * 按照bpmn文件创建部署 - 已过时
     * @param bpmFileName
     * @param deployName
     * @param categoryName
     * @Comment
     */
    @Deprecated
    public void deploy(String bpmFileName, String deployName, String categoryName) {
        RepositoryService repositoryService
                = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(bpmFileName)
                .name(deployName)
                .category(categoryName)
                .key(deployName)
                .deploy();
        System.out.println("部署的id" + deploy.getId());
        System.out.println("部署的名称" + deploy.getName());
    }

    /**
     * 查询流程定义历史版本
     * @return
     */
    public List<ProcessDefinition> getProcessDefinitions(String processDefinitionKey){
        List<ProcessDefinition> pdList=processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                //添加查询条件
                //.processDefinitionId(processDefinitionId)
                .processDefinitionKey(processDefinitionKey)
                // .processDefinitionName(processDefinitionName)
                // 排序(可以按照id/key/name/version/Cagetory排序)
                .orderByProcessDefinitionVersion().desc()
                //.count()
                // .listPage(firstResult, maxResults)
                // .singleResult()
                .list();//总的结果集数量
        //便利集合，查看内容
        for (ProcessDefinition pd:pdList) {

        }
        return pdList;
    }

    public ProcessDefinition getProcessDefinition(String deployId){
        ProcessDefinition pd = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();

        return pd;
    }

    /**删除key相同的所有不同版本的流程定义*/
    public void deleteProcessDefinitionByKey(String processDefinitionKey){
        ProcessEngine processEngine=createActivitiEngine();

        //先使用流程定义的key查询流程定义，查询出所有的版本

        RepositoryService repositoryServic=processEngine.getRepositoryService();
        ProcessDefinitionQuery query=repositoryServic.createProcessDefinitionQuery();
        List<ProcessDefinition> list=query.processDefinitionKey(processDefinitionKey).list();

        //遍历，获取每个流程定义的部署ID
        if(list!=null&&list.size()>0){
            for(ProcessDefinition pd:list){
                //获取部署ID
                String deploymentId = pd.getDeploymentId();
                processEngine.getRepositoryService()
                        .deleteDeployment(deploymentId,true);//级联删除
            }
        }
    }

    public Map<String, ProcessDefinition> queryAllLatestVersions(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService
                .createProcessDefinitionQuery();
        //升序排列
        List<ProcessDefinition> list = query.orderByProcessDefinitionVersion().asc().list();
        Map<String, ProcessDefinition> map = new LinkedHashMap<>();
        if(list!=null &&list.size()>0){
            for(ProcessDefinition pd:list){
                map.put(pd.getKey(),pd);
            }
        }
        return map;
    }

    public Deployment queryDeploymentLatestVersion(String wfKey){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pd =  query.processDefinitionKey(wfKey)
                .orderByProcessDefinitionVersion().desc()
                .singleResult();
        Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery()
                .processDefinitionKey(pd.getId())
                .singleResult();
        return deployment;
    }

    public Deployment getDeployment(String deployId){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment latestVersion = repositoryService.createDeploymentQuery()
                .deploymentId(deployId)
                .singleResult();
        return latestVersion;
    }

    /**
     * 获取所有部署
     * @return
     */
    public List<Deployment> deployList() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> list = deploymentQuery.list();
        return list;
    }

    public void removeDeploy(String deplpyId) {
        processEngine.getRepositoryService().deleteDeployment(deplpyId, true);
    }

    public byte[] getModelEditorSource(String modelId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.getModelEditorSource(modelId);
    }

    public void saveModel(Model model) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.saveModel(model);
    }

    public Model getModel(String modelId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.getModel(modelId);
    }

    /**
     * 部署流程
     * @param modelId
     */
    public void deployFromJson(String modelId) {
        String modelString = new String(getModelEditorSource(modelId));
        JSONObject modelJson = JSONObject.parseObject(modelString);
        String formKey = ((JSONObject)modelJson.get("formData")).getString("formKey");
        JSONArray childShapes = modelJson.getJSONArray("childShapes");
        BpmnModel bpmnModel = new BpmnModel();
        Process process = new Process();
        process.setId(modelJson.getJSONObject("properties").getString("process_id"));

        for (Iterator iterator = childShapes.iterator(); iterator.hasNext(); ) {
            JSONObject shape = (JSONObject) iterator.next();
            JSONObject stencil = shape.getJSONObject("stencil");
            String stencilId = stencil.get("id").toString();
            switch (stencilId) {
                case "StartNoneEvent":
                    process.addFlowElement(createStartEvent(shape.getString("resourceId")));
                    break;
                case "UserTask":
                    String assignee = shape.getJSONObject("property").getJSONObject("assignee").getString("id");
                    process.addFlowElement(createUserTask(shape.getString("resourceId"), shape.getString("name"), assignee, formKey));
                    break;
                case "EndNoneEvent":
                    process.addFlowElement(createEndEvent(shape.getString("resourceId"),formKey));
                    break;
                case "SequenceFlow":
                    process.addFlowElement(createSequenceFlow(shape.getString("startId"), shape.getString("endId"), shape.getString("conditionJSON")));
                    break;
                case "ExclusiveGateway":
                    process.addFlowElement(createExclusive(shape.getString("resourceId"), shape.getString("name")));
                    break;
            }
        }
        bpmnModel.addProcess(process);
        new BpmnAutoLayout(bpmnModel).execute();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder =
                repositoryService.createDeployment();
        builder.deploymentProperty("company_id", LoginFilter.getCurrentUser().getCompanyId());
        Deployment deployment = builder.addBpmnModel(modelJson.getJSONObject("properties").getString("name") + ".bpmn", bpmnModel)
                .name(modelJson.getJSONObject("properties").getString("name"))
                .category(formKey)
                .deploy();
        Model model = getModel(modelId);
        model.setDeploymentId(deployment.getId());
        saveModel(model);
        //更新流程属性定义
        BpmDefinitionInfo bpmDefinitionInfo = bpmDefinitionService.getDefinitionByModelId(Integer.valueOf(modelId));
        bpmDefinitionInfo.setDeployId(Integer.valueOf(deployment.getId()));
        bpmDefinitionService.updateByPrimaryKeySelective(bpmDefinitionInfo);
    }

    @Deprecated
    public void deploy(String modelId) {
        RepositoryService repositoryService
                = processEngine.getRepositoryService();
        String modelString = new String(getModelEditorSource(modelId));
        Model model = getModel(modelId);
        Deployment deploy = repositoryService.createDeployment()
                .addString("String", modelString)
                .name(model.getName())
                .category(model.getKey())
                .deploy();
    }
    /**
     * 流程图列表
     * @param pageNum
     * @return
     */
    public List<Map<String, Object>> queryModel(int pageNum) {
        List<Map<String, Object>> rtn = new ArrayList<>();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Model> models = repositoryService.createModelQuery().modelCategory(String.valueOf(LoginFilter.getCurrentUser().getCompanyId())).listPage((pageNum-1)*DEFAULT_PAGE_SIZE,DEFAULT_PAGE_SIZE);
        for (Model m : models) {
            Map<String, Object> mm = new HashMap<>();
            mm.put("id", m.getId());
            mm.put("name", m.getName());
            mm.put("lastUpdateTime", m.getLastUpdateTime());
            if(!StringUtils.isEmpty(m.getDeploymentId())) {
                ProcessDefinition pd = getProcessDefinition(m.getDeploymentId());
                mm.put("isSuspended", !pd.isSuspended());
            }else
            {
                mm.put("isSuspended", false);
            }
            rtn.add(mm);
        }
        return rtn;
    }

    /**
     * 流程图查询
     * @param pageNum
     * @param modelName
     * @return
     */
    public List<Map<String, Object>> queryModel(int pageNum, String modelName){
        List<Map<String, Object>> rtn = new ArrayList<>();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Model> models = repositoryService.createModelQuery()
                .modelCategory(String.valueOf(LoginFilter.getCurrentUser().getCompanyId()))
                .modelNameLike("%"+modelName+"%")
                .listPage(pageNum,DEFAULT_PAGE_SIZE);
        for (Model m : models) {
            Map<String, Object> mm = new HashMap<>();
            mm.put("id", m.getId());
            mm.put("name", m.getName());
            mm.put("lastUpdateTime", m.getLastUpdateTime());
            if(!StringUtils.isEmpty(m.getDeploymentId())) {
                ProcessDefinition pd = getProcessDefinition(m.getDeploymentId());
                mm.put("isSuspended", !pd.isSuspended());
            }else
            {
                mm.put("isSuspended", false);
            }
            rtn.add(mm);
        }
        return rtn;
    }

    public Map<String, Object> queryModelByWorkflowKey(String workflowKey){
        Map<String, Object> rtn = new HashMap<>();
        List<ProcessDefinition> list = getProcessDefinitions(workflowKey);
        if(list !=null && list.size()>0){
            Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentId(list.get(0).getDeploymentId()).singleResult();
            if(StringUtils.isNotEmpty(deployment.getId())){
                Model model = processEngine.getRepositoryService().createModelQuery().deploymentId(deployment.getId()).singleResult();
                if(null != model){
                    rtn.put("isSuspended",list.get(0).isSuspended());
                    rtn.put("deployId",list.get(0).getDeploymentId());
                }else{
                    rtn.put("isSuspended",null);
                }
            }

        }else{
            rtn.put("isSuspended",null);
        }
        return rtn;
    }

    public void addModelEditorSource(String modelId, byte[] sourceByte) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.addModelEditorSource(modelId, sourceByte);
    }

    public void addModelEditorSourceExtra(String modelId, byte[] sourceByte) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.addModelEditorSourceExtra(modelId, sourceByte);
    }

    public Model newModel() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.newModel();
    }

    public void delModel(String modelId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(modelId);
    }

    /**
     * 中止实例
     * @param modelId
     * @throws Exception
     */
    public void suspendTaskByProcessId(String modelId) throws Exception{
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(getModel(modelId).getDeploymentId()).singleResult();
        if(!pd.isSuspended()){
            repositoryService.suspendProcessDefinitionById(pd.getId());
        }
    }

}
