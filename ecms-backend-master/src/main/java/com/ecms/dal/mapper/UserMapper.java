package com.ecms.dal.mapper;

import com.ecms.bean.UserInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends BasicMapper<UserEntity> {
	@Select("select * from user")
	@Results(id = "userMap", value = { @Result(column = "company_id", property = "companyId"),
			@Result(column = "user_name", property = "userName"),
			@Result(column = "user_account", property = "userAccount"),
			@Result(column = "employe_id", property = "employeId"),
			@Result(column = "parent_id", property = "parentId"),
			@Result(column = "customer_id", property = "customerId")})
	List<UserEntity> tt();

	@Select("select * from user where user_account=#{account}")
	@ResultMap("userMap")
	UserEntity selectByAccount(String account);

	@Update("update user set password = #{password} where id = #{id}")
	void changePassword(@Param("id")Integer id,@Param("password")String password);

	@Select("SELECT id FROM user WHERE employee_number = #{employeeNumber} AND company_id = #{companyId}")
	UserInfo searchByEmployeNumber(@Param("employeeNumber")Integer employeeNumber, @Param("companyId")Integer companyId);

	@Select("SELECT customer_id FROM user WHERE id = #{id} AND company_id = #{companyId}")
	@Results(id = "userCustomerIdMap", value = { @Result(column = "company_id", property = "companyId"),
			@Result(column = "customer_id", property = "customerId")})
	UserEntity searchById(@Param("id")Integer id, @Param("companyId")Integer companyId);

	@Select("select * from user where employe_id=#{employeeId}")
	@ResultMap("userMap")
	UserEntity searchByIdSameCompany(@Param("employeeId")Integer employeeId);

	@Select("select * from user where id=#{userId}")
	@ResultMap("userMap")
	UserEntity getById(@Param("userId")Integer userId);
}
