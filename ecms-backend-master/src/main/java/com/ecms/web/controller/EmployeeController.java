package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.RedisCache;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.PostEntity;
import com.ecms.excel.EmployeeTemplate;
import com.ecms.service.BaseService;
import com.ecms.service.DepartmentService;
import com.ecms.service.EmployeeService;
import com.ecms.service.PostService;
import com.ecms.utils.ExcelImportUtil;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ecms.common.BusinessConstants.PAGE_SIZE;

@RestController
@RequestMapping("employee")
public class EmployeeController extends BaseController<EmployeeEntity, EmployeeInfo> {

    @Value("${env}")
    private String env;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private PostService postService;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseService<EmployeeEntity, EmployeeInfo> getBaseService() {
        return employeeService;
    }

    @RequestMapping("listIncludePost")
    public Result<Pager<EmployeeInfo>> list(EmployeeSearchParam param) {
        try {
            employeeService.setCriteria(param);
        } catch (Exception e) {
            return ResultUtils.success(new Pager<>());
        }
        Pager<EmployeeInfo> pager = employeeService.getByCondition(param.getPageNum(), DEFAULT_PAGE_SIZE);
        return ResultUtils.success(pager);
    }

    @RequestMapping("/info")
    public Result<EmployeeIndex> info(@RequestParam(value = "employeeId", required = false, defaultValue = "0") int employeeId) {
        EmployeeIndex employeeIndex = new EmployeeIndex();
        EmployeeInfo employeeInfo = new EmployeeInfo();
        if (employeeId > 0) {
            employeeInfo = employeeService.getModel(employeeId);
            if (employeeInfo.getDepartmentId() != null) {
                if (employeeService.setDepartmentInfo(employeeInfo) == 3) {
                    DepartmentEntity departmentEntity = redisCache.get(DepartmentEntity.class, employeeInfo.getDepartmentId());
                    employeeIndex.setThirdDepartment(departmentService.getChildren(departmentEntity.getParentId()));
                }
                employeeIndex.setSecondDepartment(departmentService.getChildren(employeeInfo.getRootDepartmentId()));
            }
        }
        employeeIndex.setEmployeeInfo(employeeInfo);
        employeeIndex.setFirstDepartment(departmentService.getFirstLists());
        employeeIndex.setPostInfoList(ExchangeUtils.exchangeList(redisCache.getList(PostEntity.class), PostInfo.class));
        return ResultUtils.success(employeeIndex);
    }

    @RequestMapping("/getEmployeePic")
    public Result<String> getEmployeePic(@RequestParam(value = "employeeId", required = false, defaultValue = "0") int employeeId) {
        EmployeeInfo employeeInfo = new EmployeeInfo();
        String employeePicAddress = null;
        if (employeeId > 0) {
            employeeInfo = employeeService.getModel(employeeId);
            if(employeeInfo.getEmployeePic() != null){
                employeePicAddress = employeeService.getFileUrl(employeeInfo.getEmployeePic());
            }
        }
        return ResultUtils.success(employeePicAddress);
    }

    @RequestMapping("/removeDepartment")
    public Result<Void> removeDepartment(int id) {
        employeeService.removeDepartment(id);
        return ResultUtils.success();
    }

    @RequestMapping("/simple")
    public Result<List<SimpleEmployeeInfo>> getSimleList(int departmentId) {
        return ResultUtils.success(employeeService.getSimpleList(departmentId));
    }

    @RequestMapping("/updateDepartment")
    public Result<Void> updateDepartment(EmployeeUpdateDepartmentParam param) {
        employeeService.updateDepartmentInfo(param.getDepartmentId(), param.getIdList());
        return ResultUtils.success();
    }

    @RequestMapping("/listEmployee")
    public Result<Pager<EmployeeInfo>> login(int pageNum, EmployeeInfo employeeInfo) {
        employeeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        ExchangeUtils.convertBlank2Null(employeeInfo);
        Pager<EmployeeInfo> pager = employeeService.getEmployeeList(pageNum, DEFAULT_PAGE_SIZE, employeeInfo);
        return ResultUtils.success(pager);
    }

    @RequestMapping("/listEmployeeByInfo")
    public Result<Pager<EmployeeInfo>> listEmployeeByInfo(int pageNum, EmployeeInfo employeeInfo) {
        ExchangeUtils.convertBlank2Null(employeeInfo);
        employeeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Pager<EmployeeInfo> pager = employeeService.getEmployeeList(pageNum, PAGE_SIZE, employeeInfo);
        return ResultUtils.success(pager);
    }

    @RequestMapping("/status/update")
    public Result<Void> updateStatus(int id) {
        employeeService.dimission(id);
        return ResultUtils.success();
    }

    @Override
    protected List<EmployeeTemplate> getListForDownload() {
        return employeeService.getExportList();
    }

    @Override
    protected List<EmployeeTemplate> getTemplateForDownload() {
        List<EmployeeTemplate> list = new ArrayList<>();
        list.add(new EmployeeTemplate());
        return list;
    }

    @Override
    protected void uploadData(MultipartFile file) throws IOException {
        List<Object> list = ExcelImportUtil.importExcel(file.getInputStream(), EmployeeTemplate.class);
        employeeService.upload(list);
    }

    @RequestMapping("/getListEmployee")
    public Result<List<EmployeeInfo>> getListEmployee(EmployeeInfo employeeInfo) {
        ExchangeUtils.convertBlank2Null(employeeInfo);
        employeeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<EmployeeInfo> employeeInfoList = employeeService.getListBy(employeeInfo);
        if (employeeInfoList.size()!=0){
            for (int i=0;i<employeeInfoList.size();i++){
                DepartmentInfo di = departmentService.getModel(employeeInfoList.get(i).getDepartmentId());
                if (di.getId()!=null){
                    employeeInfoList.get(i).setDepartmentName(di.getDepartmentName());
                }
            }
        }
        return ResultUtils.success(employeeInfoList);
    }

    @RequestMapping("/save")
    public Result<Void> saveEmployee(EmployeeInfo employeeInfo) {
        employeeInfo.setEmployeeNumber(employeeService.getNextEmployeeNumber());
        employeeService.saveOrUpdate(employeeInfo);
        return ResultUtils.success();
    }

}
