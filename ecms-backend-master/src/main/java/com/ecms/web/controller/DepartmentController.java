package com.ecms.web.controller;

import com.ecms.bean.DepartmentIndex;
import com.ecms.bean.DepartmentInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DepartmentService;
import com.ecms.service.EmployeeService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController extends BaseController<DepartmentEntity, DepartmentInfo> {

    @Resource
    private DepartmentService departmentService;
    @Resource
    private EmployeeService employeeService;

    @Override
    protected BaseService<DepartmentEntity, DepartmentInfo> getBaseService() {
        return departmentService;
    }

    @RequestMapping("/first")
    public Result<Pager<DepartmentInfo>> getFirstLists(int pageNum, int pageSize, DepartmentInfo info) {
        Pager<DepartmentInfo> list = departmentService.getFirstLists(pageNum, pageSize, info);
        return ResultUtils.success(list);
    }

    @RequestMapping("insertDepartment")
    public Result<Void> insertDepartment(DepartmentInfo info) {
        info.setDepartmentNumber(departmentService.getNextNumber());
        getBaseService().insertBy(info);
        return ResultUtils.success();
    }

    @RequestMapping("/children")
    public Result<List<DepartmentInfo>> getChildren(int id) {
        List<DepartmentInfo> list = departmentService.getChildren(id);
        return ResultUtils.success(list);
    }

    @RequestMapping("/addChild")
    public Result<Void> addChild(int id, String name) {
        departmentService.addChild(id, name);
        return ResultUtils.success();
    }

    @RequestMapping("/index")
    public Result<DepartmentIndex> index(int id) {
        DepartmentIndex index = new DepartmentIndex();
        DepartmentInfo info = departmentService.getInfo(id);
        index.setDepartmentInfo(info);
        index.setChildren(departmentService.getChildren(id));
        index.setEmployeeInfoList(employeeService.getValidList());
        index.setChildEmployeeInfoList(employeeService.getByDepartmentId(id));
        index.setThirdDepartment(info.getParentId() != null && info.getParentId() != info.getRootId());
        return ResultUtils.success(index);
    }

    @RequestMapping("del")
    public Result<Void> del(Integer id) {
        departmentService.del(id);
        return ResultUtils.success();
    }

}
