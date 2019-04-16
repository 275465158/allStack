package com.ecms.test;

import com.ecms.EcmsApplication;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.manager.DepartmentManager;
import com.ecms.manager.EmployeeManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcmsApplication.class)
@EnableAutoConfiguration
public class DepartmentTest {

    @Resource
    DepartmentManager departmentManager;
    @Resource
    RedisCache redisCache;
    @Resource
    EmployeeManager employeeManager;

    @Test
    public void test() {
//        DepartmentEntity departmentEntity = new DepartmentEntity();
//        departmentEntity.setDepartmentName("研发部");
//        departmentEntity.setCompanyId(1);
//        departmentManager.insertBy(departmentEntity);
//        Condition condition = new Condition(DepartmentEntity.class);
//        Example.Criteria criteria = condition.createCriteria();
//        criteria.andEqualTo("status", 1);
//        criteria.andIsNull("parentId");
//        criteria.andLike("departmentName", "%发%");
//        criteria.andEqualTo("status", 1);
//        List<DepartmentEntity> list = departmentManager.getByCondition(condition);
//        System.out.println(list.size());
        DepartmentEntity entity = redisCache.get(DepartmentEntity.class, 1);
        Collection<DepartmentEntity> list = redisCache.getList(DepartmentEntity.class);
        for (DepartmentEntity departmentEntity : list) {
            System.out.println(departmentEntity.getId());
        }
        System.out.println("--------");
    }

    @Test
    public void emplyeeNumberTest() {
        int number = employeeManager.getNextEmployeeNumber();
        System.out.println(number);
    }
}
