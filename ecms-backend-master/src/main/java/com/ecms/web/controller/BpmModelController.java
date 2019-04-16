package com.ecms.web.controller;

import com.ecms.bean.BpmDefinitionInfo;
import com.ecms.bean.DepartmentInfo;
import com.ecms.bean.EmployeeInfo;

import com.ecms.bean.UserInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.service.*;

import com.ecms.utils.ResultUtils;

import com.ecms.web.filter.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ecms.common.BusinessConstants.*;


@RestController
@RequestMapping("bpm")
public class BpmModelController {

    @Resource
    private EmployeeService employeeService;
    @Resource
    private UserService userService;
    @Resource
    private ActivitiModelService tasksService;
    @Resource
    private BpmDefinitionService bpmDefinitionService;
    @Resource
    private PurchaseOrdersService purchaseOrdersService;
    @Resource
    private MaterialBomService materialBomService;
    @Resource
    private DepartmentService departmentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 保存流程图
     * @param values
     * @return
     */
    @RequestMapping("models/saveModel")
    public Result<Void> saveModel( @RequestBody MultiValueMap<String, String> values){
        String modelId = values.getFirst("modelId");
        try {
            //更新流程设计
            Model model = tasksService.getModel(modelId);

            ObjectNode modelJson = null;
            if (model != null) {
                modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

                modelJson.put("name", values.getFirst("name"));
                modelJson.put("category", LoginFilter.getCurrentUser().getCompanyId());
                modelJson.put("description", values.getFirst("description"));
                model.setMetaInfo(modelJson.toString());
                model.setName(values.getFirst("name"));
                model.setCategory(String.valueOf(LoginFilter.getCurrentUser().getCompanyId()));
            }else{
                model = tasksService.newModel();
                modelJson = objectMapper.createObjectNode();
                modelJson.put("name", values.getFirst("name"));
                modelJson.put("category", LoginFilter.getCurrentUser().getCompanyId());
                modelJson.put("description", values.getFirst("description"));
                model.setMetaInfo(modelJson.toString());
                model.setCategory(String.valueOf(LoginFilter.getCurrentUser().getCompanyId()));
                model.setName(values.getFirst("name"));
            }
            tasksService.saveModel(model);
            tasksService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes(StandardCharsets.UTF_8));

            //更新流程属性
            BpmDefinitionInfo bpmDefinitionInfo = bpmDefinitionService.getDefinitionByModelId(Integer.valueOf(model.getId()));
            if(bpmDefinitionInfo!=null) {
                bpmDefinitionInfo.setDeployXml(values.getFirst("json_xml"));
                bpmDefinitionInfo.setDeployDesc(model.getMetaInfo());
                bpmDefinitionInfo.setDeploySubject(model.getName());
                bpmDefinitionInfo.setTaskNameRules(values.getFirst("taskNameRules"));
                bpmDefinitionInfo.setDeployId(Integer.valueOf(model.getDeploymentId()));
            }else{
                bpmDefinitionInfo = new BpmDefinitionInfo();
                bpmDefinitionInfo.setModelId(Integer.valueOf(model.getId()));
                bpmDefinitionInfo.setDeploySubject(model.getName());
                bpmDefinitionInfo.setDeployDesc(model.getMetaInfo());
                bpmDefinitionInfo.setDeployXml(values.getFirst("json_xml"));
                bpmDefinitionInfo.setDefKey(values.getFirst("key"));
                bpmDefinitionInfo.setTaskNameRules(values.getFirst("taskNameRules"));
            }
            bpmDefinitionService.saveOrUpdate(bpmDefinitionInfo);
        } catch (Exception e) {
            throw new ActivitiException("Error saving model", e);
        }
        return ResultUtils.success();
    }

    /**
     * 获得流程图列表
     * @param pageNum
     * @return
     */
    @RequestMapping("models/listModel")
    public Result<List<Map<String, Object>>> listModel(Integer pageNum){
        return ResultUtils.success(tasksService.queryModel(pageNum));
    }

    /**
     * 查询流程图 - 按名称
     * @param pageNum
     * @param modelName
     * @return
     */
    @RequestMapping("models/searchModel")
    public Result<List<Map<String,Object>>> searchModel(Integer pageNum, String modelName){
        return ResultUtils.success(tasksService.queryModel(pageNum, modelName));
    }

    /**
     * 获取流程图内容
     * @param modelId
     * @return
     */
    @RequestMapping("models/getModel")
    public Result<Map<String, Object>> getModel(String modelId){
        Map<String, Object> rtnModel = new HashMap<>();
        Model model = tasksService.getModel(modelId);
        //模型名称
        rtnModel.put("modelName",model.getName());
        rtnModel.put("metaInfo",model.getMetaInfo());
        Deployment lastDeployment = null;
        if(StringUtils.isNotEmpty( model.getDeploymentId())) {
            lastDeployment = tasksService.getDeployment(model.getDeploymentId());
            //模型对应的功能模块
            rtnModel.put("formKey",lastDeployment.getCategory());
        }
        return ResultUtils.success(rtnModel);

    }

    /**
     * 获取流程图内容 - 图例内容
     * @param modelId
     * @return
     */
    @RequestMapping("models/getModelJson")
    public Result<String> getModelJson(String modelId){
        byte[] json_xml_b = tasksService.getModelEditorSource(modelId);
        String json_xml = new String(json_xml_b);
        return ResultUtils.success(json_xml);
    }

    /**
     * 查询用户
     * @param pageNum
     * @param departmentName
     * @param postName
     * @param name
     * @return
     */
    @RequestMapping("searchUserList")
    public Result<Pager<UserInfo>> userList(int pageNum, String departmentName, String postName, String name){
        EmployeeInfo searchEmployeeInfo = new EmployeeInfo();
        searchEmployeeInfo.setDepartmentName(departmentName);
        searchEmployeeInfo.setPostName(postName);
        searchEmployeeInfo.setName(name);
        List<EmployeeInfo> employeeInfoList = employeeService.getValidListByInfo(searchEmployeeInfo);
        if(employeeInfoList.size()==0){
//            return ResultUtils.failure("没有找到这个员工信息!");
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        for(EmployeeInfo employeeInfo : employeeInfoList){
            UserInfo user = userService.getByIdInSameCompany(employeeInfo.getId());
            userInfoList.add(user);
        }
        Pager<UserInfo> userInfoPager = new Pager<>(pageNum,PAGE_SIZE,userInfoList.size(),userInfoList);
        return ResultUtils.success(userInfoPager);
    }

    /**
     * 获得用户列表
     * @param pageNum
     * @return
     */
    @RequestMapping("userList")
    public Result<Pager<UserInfo>> user(int pageNum){
        Pager<UserInfo> pager = userService.getUserList(pageNum, PAGE_SIZE, new UserInfo());
        return ResultUtils.success(pager);
    }

    @RequestMapping("departUserList")
    public Result<List<Map<String, Object>>> departUser(String departmentName, String postName, String employeeName){
        List<Map<String, Object>> rtnList = new ArrayList<>();
        DepartmentInfo searchDepart = new DepartmentInfo();
        if(StringUtils.isNotEmpty(departmentName)) {
            searchDepart.setDepartmentName(departmentName);
        }
        List<DepartmentInfo> departmentInfoList = departmentService.getListBy(searchDepart);
        for(DepartmentInfo depart : departmentInfoList){
            EmployeeInfo searchEmployee = new EmployeeInfo();
            if(StringUtils.isNotEmpty(postName)) {
                searchEmployee.setPostName(postName);
            }
            if(StringUtils.isNotEmpty(employeeName)){
                searchEmployee.setName(employeeName);
            }
            List<EmployeeInfo> employeeInfoList = employeeService.getValidListByInfo(searchEmployee);
            if(employeeInfoList.size()>0){
                Map<String, Object> resultDepart = new HashMap<>();
                resultDepart.put("departId", depart.getId());
                resultDepart.put("departName", depart.getDepartmentName());
                resultDepart.put("id", depart.getId());
                resultDepart.put("label", depart.getDepartmentName());
                resultDepart.put("pid", "0");
                List<Map<String, Object>> resultEmployeeList = new ArrayList<>();
                for(EmployeeInfo employeeInfo : employeeInfoList){

                }
                resultDepart.put("children", resultEmployeeList);
            }
        }
        return ResultUtils.success(rtnList);
    }


    /**
     * 部署流程图例
     * @param modelId
     * @return
     */
    @RequestMapping("models/deploy")
    public Result<Void> deploy(String modelId){
        tasksService.deployFromJson(modelId);
        return ResultUtils.success();
    }


    /**
     * 停用、删除流程时所有待评审记录改为评审通过，分流方法
     */
    public void setReviewStatusCompleted(String modelId){
        Model model = tasksService.getModel(modelId);
        if(StringUtils.isNotEmpty(model.getDeploymentId())){
            String workflowKey =tasksService.getDeployment(model.getDeploymentId()).getCategory();
            switch (workflowKey){
                case WORKFLOW_PURCHASE_ORDER:
                    purchaseOrdersService.suspendedWorkflow();
                    break;
                case WORKFLOW_MATERIAL_BOM:
                    materialBomService.suspendedWorkflow();
                    break;
            }
        }
    }
    /**
     * 停用流程实例
     * @param modelId
     * @return
     * @throws Exception
     */
    @RequestMapping("models/suspendModel")
    public Result<Map<String, Object>> suspendModel(String modelId) throws Exception {
        Map<String, Object> rtnModel = new HashMap<>();
        //停用流程实例
        tasksService.suspendTaskByProcessId(modelId);
        //改变当前列表中未启动为审核通过
        setReviewStatusCompleted(modelId);
        return ResultUtils.success(rtnModel);
    }

    /**
     * 删除流程图
     * @param modelId
     * @return
     */
    @RequestMapping("models/deleteModel")
    public Result<Void> delModel(String modelId){
        //改变当前列表中未启动为审核通过
        setReviewStatusCompleted(modelId);
        //删除属性
        bpmDefinitionService.delByModelId(modelId);
        //删除设计
        tasksService.delModel(modelId);
        return ResultUtils.success();
    }

    @RequestMapping("models/getStatus")
    public Result<Map<String, Object>> getProcessDefinitionStatus(String workflowKey){
        return ResultUtils.success(tasksService.queryModelByWorkflowKey(workflowKey));
    }

    @RequestMapping("models/isAdmin")
    public Result<Map<String, Object>> isAdmin(){
        Map<String, Object> result = new HashMap<String, Object>();
        UserInfo admin = LoginFilter.getCurrentUser();
        if(admin.getId().equals(1)){
            result.put("isAdmin", true);
        }
        return ResultUtils.success(result);
    }

}
