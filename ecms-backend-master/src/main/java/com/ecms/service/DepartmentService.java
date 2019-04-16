package com.ecms.service;

import com.ecms.bean.DepartmentInfo;
import com.ecms.common.Pager;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.DepartmentManager;
import com.ecms.manager.EmployeeManager;
import com.ecms.utils.ExchangeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService extends BaseService<DepartmentEntity, DepartmentInfo> {

    @Resource
    private DepartmentManager departmentManager;

    @Resource
    private EmployeeManager employeeManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<DepartmentEntity> getManager() {
        return departmentManager;
    }

    @Override
    protected void doAfterUpdate(DepartmentEntity departmentEntity) {
        redisCache.update(departmentEntity);
    }

    @PostConstruct
    public void init() {
        List<DepartmentEntity> list = getValidStatusList();
        redisCache.refresh(list);
    }

    /**
     * 获取一级部门
     * @param pageNum
     * @param pageSize
     * @param info
     * @return
     */
    public Pager<DepartmentInfo> getFirstLists(int pageNum, int pageSize, DepartmentInfo info) {
        Example.Criteria criteria = getDefaultCriteria();
        if (StringUtils.isNotBlank(info.getAdminName())) {
            List<EmployeeEntity> list = employeeManager.getByEmployeeName(info.getAdminName());
            if (CollectionUtils.isEmpty(list)) {
                throw new CommonBusinessException("部门主管输入有误，未找到对应员工");
            }
            List<Integer> idList = list.stream().map(EmployeeEntity::getId).collect(Collectors.toList());
            criteria.andIn("departmentAdminId", idList);
        }
        if (StringUtils.isNotBlank(info.getDepartmentNumberDisplay())) {
            criteria.andEqualTo("departmentNumber", info.getDepartmentNumber());
        }
        if (StringUtils.isNotBlank(info.getDepartmentName())) {
            criteria.andLike("departmentName", "%" + info.getDepartmentName() + "%");
        }
        criteria.andIsNull("parentId");
        long total = getCountByConditionForPager();
        List<DepartmentEntity> entityList = getEntityListByCondition(pageNum, pageSize);
        List<DepartmentInfo> infoList = entityList.stream().map(x->convertEntity(x, true)).collect(Collectors.toList());
        return new Pager<>(pageNum, pageSize, total, infoList);
    }

    /**
     * 获取一级部门
     * @return
     */
    public List<DepartmentInfo> getFirstLists() {
        Example.Criteria criteria = getDefaultCriteria();
        criteria.andIsNull("parentId");
        return getDepartmentListByCondition();
    }

    public List<DepartmentInfo> getChildren(int departmentId) {
        Example.Criteria criteria = getDefaultCriteria();
        criteria.andEqualTo("parentId", departmentId);
        return getDepartmentListByCondition();
    }

    public DepartmentInfo getInfo(int id) {
        DepartmentEntity entity = getEntity(id);
        return convertEntity(entity, true);
    }

    private List<DepartmentInfo> getDepartmentListByCondition() {
        List<DepartmentEntity> list = getEntityListByCondition();
        return list.stream().map(x->convertEntity(x, false)).collect(Collectors.toList());
    }

    private DepartmentInfo convertEntity(DepartmentEntity entity, boolean needTotalEmployees) {
        DepartmentInfo info = ExchangeUtils.exchangeObject(entity, DepartmentInfo.class);
        if (entity.getDepartmentAdminId() != null) {
            EmployeeEntity adminEmployee = employeeManager.getEntityById(entity.getDepartmentAdminId());
            info.setAdminName(adminEmployee.getName());
        }
        // 设置下级部门数
        List<DepartmentEntity> list = departmentManager.getChildren(entity.getId());
        info.setChildren(list.size());
        // 设置部门人数
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setStatus(STATUS_DEFAULT);
        employeeEntity.setDepartmentId(entity.getId());
        long employees = employeeManager.getCountBy(employeeEntity);
        info.setEmployees((int)employees);
        // 设置部门总人数
        if (needTotalEmployees) {
            long childEmployees = 0;
            if (list.size() > 0) {
                Condition condition = new Condition(EmployeeEntity.class);
                Example.Criteria eCriteria = condition.createCriteria();
                eCriteria.andEqualTo("status", STATUS_DEFAULT);
                eCriteria.andIn("departmentId", ExchangeUtils.getIdList(list));
                childEmployees = employeeManager.getCountByCondition(condition);
            }
            info.setTotalEmployees(childEmployees + info.getEmployees());
        }
        return info;
    }

    public void addChild(int currentDepartmentId, String name) {
        DepartmentEntity currentDepartment = getEntity(currentDepartmentId);
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentName(name);
        departmentEntity.setParentId(currentDepartmentId);
        departmentEntity.setDepartmentNumber(getNextNumber());
        if (currentDepartment.getParentId() != null) {
            departmentEntity.setRootId(currentDepartment.getParentId());
        } else {
            departmentEntity.setRootId(currentDepartmentId);
        }
        insert(departmentEntity);
        doAfterUpdate(departmentEntity);
    }

    @Transactional
    public void del(int id) {
        List<DepartmentEntity> list = departmentManager.getChildren(id);
        List<Integer> idList = ExchangeUtils.getIdList(list);
        idList.add(id);
        // 删除部门及所有子部门
        DepartmentEntity entity = new DepartmentEntity();
        entity.setStatus(STATUS_INVALID);
        Condition condition = new Condition(eClass);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andIn("id", idList);
        departmentManager.updateByConditionSelective(entity, condition);
        // 删除相应员工对应的部门信息
        employeeManager.removeDepartmentInfo(idList);
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setStatus(STATUS_INVALID);
        departmentEntity.setId(id);
        doAfterUpdate(departmentEntity);
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_department_";
    }

}
