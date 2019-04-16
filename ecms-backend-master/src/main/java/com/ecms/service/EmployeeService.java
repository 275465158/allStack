package com.ecms.service;

import com.ecms.bean.EmployeeInfo;
import com.ecms.bean.EmployeeSearchParam;
import com.ecms.bean.SimpleEmployeeInfo;
import com.ecms.common.Pager;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.PostEntity;
import com.ecms.excel.EmployeeTemplate;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.DepartmentManager;
import com.ecms.manager.EmployeeManager;
import com.ecms.manager.PostManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.FileUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ecms.utils.ExcelImportUtil.checkObjFieldIsNotNull;

@Service
public class EmployeeService extends BaseService<EmployeeEntity, EmployeeInfo> {

    @Value("${env}")
    private String env;

    @Resource
    private EmployeeManager employeeManager;
    @Resource
    private DepartmentManager departmentManager;
    @Resource
    private PostManager postManager;
    @Resource
    private RedisCache redisCache;

    @Override
    protected BaseManager<EmployeeEntity> getManager() {
        return employeeManager;
    }

    @Override
    protected void setCustomProperties(List<EmployeeInfo> list) {
        for (EmployeeInfo employeeInfo : list) {
            setDepartmentInfo(employeeInfo);
            setPostInfo(employeeInfo);
        }
    }

    public void setCriteria(EmployeeSearchParam param) {
        Example.Criteria criteria = getDefaultCompanyCriteria();
        if (param.getEmployeeNumber() != null) {
            criteria.andEqualTo("employeeNumber", param.getEmployeeNumber());
        }
        if (param.getName() != null) {
            criteria.andLike("name", "%" + param.getName() + "%");
        }
        // 在职
        if (param.getStatus() != null && param.getStatus() == 1) {
            criteria.andEqualTo("status", 1);
        }
        // 离职
        if (param.getStatus() != null && param.getStatus() == 0) {
            criteria.andEqualTo("status", 0);
        }
        if (!StringUtils.isEmpty(param.getDepartmentName())) {
            List<Integer> list = departmentManager.getDepartmentIdsByName(param.getDepartmentName());
            Assert.isTrue(list != null, "部门信息未找到");
            criteria.andIn("departmentId", list);
        }
        if (!StringUtils.isEmpty(param.getPostName())) {
            List<Integer> list = postManager.getPostIdsByName(param.getPostName());
            Assert.isTrue(list != null, "岗位信息未找到");
            criteria.andIn("postId", list);
        }
    }

    private void setPostInfo(EmployeeInfo employeeInfo) {
        if (employeeInfo.getPostId() == null) {
            logger.error("员工[{}]没有岗位信息", employeeInfo.getId());
            return;
        }
        PostEntity postEntity = redisCache.get(PostEntity.class, employeeInfo.getPostId());
        if (postEntity != null) {
            employeeInfo.setPostName(postEntity.getName());
        }
    }

    public List<EmployeeInfo> getByDepartmentId(int departmentId) {
        Example.Criteria criteria = getDefaultCriteria();
        criteria.andEqualTo("departmentId", departmentId);
        List<EmployeeInfo> list = getByCondition();
        list.stream().filter(x->x.getPostId() != null).forEach(x->x.setPostName(redisCache.get(PostEntity.class, x.getPostId()).getName()));
        return list;
    }

    public long getCountByPostId(int postId) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setStatus(1);
        entity.setPostId(postId);
        return employeeManager.getCountBy(entity);
    }

    public List<SimpleEmployeeInfo> getSimpleList(int departmentId) {
        int companyId = LoginFilter.getCurrentUser().getCompanyId();
        List<SimpleEmployeeInfo> list = employeeManager.getSimpleList(companyId, departmentId);
        for (SimpleEmployeeInfo sei : list) {
            if (sei.getDepartmentId() != null) {
                sei.setDepartmentName(redisCache.get(DepartmentEntity.class, sei.getDepartmentId()).getDepartmentName());
            }
        }
        return list;
    }

    public int setDepartmentInfo(EmployeeInfo employeeInfo) {
        int level = 1;
        if (employeeInfo.getDepartmentId() == null || employeeInfo.getRootDepartmentId() == null) {
            return 0;
        }
        employeeInfo.setFirstDepartmentName(redisCache.get(DepartmentEntity.class, employeeInfo.getRootDepartmentId()).getDepartmentName());
        employeeInfo.setDepartmentName(redisCache.get(DepartmentEntity.class, employeeInfo.getDepartmentId()).getDepartmentName());
        // 两个部门ID不等，说明不是一级目录，需要判断下是否是第三级目录，并获取相应的部门列表
        if (!employeeInfo.getDepartmentId().equals(employeeInfo.getRootDepartmentId())) {
            DepartmentEntity departmentEntity = redisCache.get(DepartmentEntity.class, employeeInfo.getDepartmentId());
            if (departmentEntity.getParentId() == null) {
                logger.error("部门信息异常，用户id={}, 部门id={}", employeeInfo.getId(), departmentEntity.getId());
                throw new CommonBusinessException("部门信息异常");
            }
            // 二级目录
            if (departmentEntity.getParentId().equals(departmentEntity.getRootId())) {
                employeeInfo.setSecondDepartmentId(employeeInfo.getDepartmentId());
                employeeInfo.setSecondDepartmentName(departmentEntity.getDepartmentName());
                level = 2;
            } else { // 三级目录
                employeeInfo.setThirdDepartmentId(employeeInfo.getDepartmentId());
                employeeInfo.setSecondDepartmentId(departmentEntity.getParentId());
                employeeInfo.setThirdDepartmentName(departmentEntity.getDepartmentName());
                employeeInfo.setSecondDepartmentName(redisCache.get(DepartmentEntity.class, departmentEntity.getParentId()).getDepartmentName());
                level = 3;
            }
        }
        return level;
    }

    public void removeDepartment(int id) {
        employeeManager.removeDepartmentInfo(id);
    }

    public void updateDepartmentInfo(int departmentId, List<Integer> idList) {
        if (!CollectionUtils.isEmpty(idList) && departmentId > 0) {
            DepartmentEntity entity = redisCache.get(DepartmentEntity.class, departmentId);
            Integer rootDepartmentId = entity.getRootId() == null ? entity.getId() : entity.getRootId();
            employeeManager.updateDepartmentInfo(departmentId, rootDepartmentId, idList);
        }
    }

    public Pager<EmployeeInfo> getEmployeeList(int pageNum, int pageSize, EmployeeInfo employeeInfo) {
        EmployeeEntity employeeEntity = ExchangeUtils.exchangeObject(employeeInfo, EmployeeEntity.class);
        List<EmployeeEntity> list = employeeManager.getEmployeeList(pageNum, pageSize, employeeEntity);
        long total = employeeManager.getTotal(employeeEntity);
        List<EmployeeInfo> userList = ExchangeUtils.exchangeList(list, EmployeeInfo.class);
        Pager<EmployeeInfo> pager = new Pager<EmployeeInfo>(pageNum, pageSize, total, userList);
        return pager;
    }

    public void dimission(int id) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setStatus(STATUS_INVALID);
        entity.setDimissionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        entity.setId(id);
        employeeManager.updateByPrimaryKeySelective(entity);
    }

    public List<EmployeeTemplate> getExportList() {
        List<EmployeeTemplate> result = new ArrayList<>();
        List<EmployeeInfo> EmployeeInfo = getValidCompanyList();
        for (EmployeeInfo info : EmployeeInfo) {
            EmployeeTemplate employeeTemplate = ExchangeUtils.exchangeObject(info, EmployeeTemplate.class);
            employeeTemplate.setStatusDisplay(info.getStatus() == 0 ? "离职" : "在职");
            if (info.getPostId() != null) {
                PostEntity postEntity = redisCache.get(PostEntity.class, info.getPostId());
                if (postEntity != null) {
                    employeeTemplate.setPostName(postEntity.getName());
                }
            }
            result.add(employeeTemplate);
        }
        return result;
    }

    public void upload(List<Object> list) {
        Map<String, DepartmentEntity> department = new HashMap<>();
        Map<String, Integer> post = new HashMap<>();
        Collection<DepartmentEntity> departmentList = redisCache.getList(DepartmentEntity.class);
        for (DepartmentEntity entity : departmentList) {
            department.put(entity.getDepartmentName(), entity);
        }
        Collection<PostEntity> postList = redisCache.getList(PostEntity.class);
        for (PostEntity entity : postList) {
            post.put(entity.getName(), entity.getId());
        }
        int count=0;
        for (Object obj : list) {
        	count++;
            EmployeeTemplate template = (EmployeeTemplate)obj;
            EmployeeInfo info = ExchangeUtils.exchangeObject(template, EmployeeInfo.class);
            info.setStatus("在职".equals(template.getStatusDisplay()) ? 1 : 0);
            // 设置根部门ID
            try {//过滤空行
                if (!checkObjFieldIsNotNull(template)){
                    continue;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            // 设置部门ID
            if (!StringUtils.isEmpty(template.getDepartmentName())) {
                DepartmentEntity departmentEntity = department.get(template.getDepartmentName());
                if (departmentEntity == null) {
                    throw new CommonBusinessException("第"+count+"行数据中一级部门不存在");
                }
                info.setDepartmentId(departmentEntity.getId());
                Integer rootId = departmentEntity.getRootId();
                info.setRootDepartmentId(rootId == null ? departmentEntity.getId() : rootId);
            }else {
                throw new CommonBusinessException("第"+count+"行数据中部门未填");
            }
            //设置针对excel的时间
            Calendar c = new GregorianCalendar(1900,0,-1);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isEmpty(template.getBirthday())) {
                Date date= DateUtils.addDays(c.getTime(),Integer.valueOf(template.getBirthday()));
                info.setBirthday(simpleDateFormat.format(date));
            }
            if (!StringUtils.isEmpty(template.getJoinDate())) {
                Date date= DateUtils.addDays(c.getTime(),Integer.valueOf(template.getJoinDate()));
                info.setJoinDate(simpleDateFormat.format(date));
            }
            if (!StringUtils.isEmpty(template.getDimissionDate())) {
                Date date= DateUtils.addDays(c.getTime(),Integer.valueOf(template.getDimissionDate()));
                info.setDimissionDate(simpleDateFormat.format(date));
            }
            // 设置岗位ID
            if (!StringUtils.isEmpty(template.getPostName())) {
                Integer postId = post.get(template.getPostName());
                if (postId == null) {
                    throw new CommonBusinessException("第"+count+"行数据中岗位未找到");
                }
                info.setPostId(postId);
            }else {
                throw new CommonBusinessException("第"+count+"行数据中岗位未填");
            }
            info.setEmployeeNumber(getNextEmployeeNumber());
            try {
                insertBy(info);
            } catch (DuplicateKeyException e) {
                throw new CommonBusinessException("第"+count+"行身份证号已重复");
            } catch (CannotAcquireLockException e) {
                throw new CommonBusinessException("第"+count+"行身份证号已重复");
            }
        }
    }

    public int getNextEmployeeNumber() {
        return employeeManager.getNextEmployeeNumber();
    }

    @Override
    public String uploadFileSimple(MultipartFile file) {
        return FileUtils.uploadFile(file, env, "employee-pic/");
    }

    public String getFileUrl(String fileName) {
        return FileUtils.getFileUrl(env, fileName);
    }

    @Override
    public InputStream getFileInputStream(String filePath) {
        return FileUtils.getInputStream(env, filePath);
    }

}
