package com.ecms.manager;

import com.ecms.bean.UserInfo;
import com.ecms.dal.entity.UserEntity;
import com.ecms.dal.mapper.UserMapper;
import com.ecms.utils.ExchangeUtils;
import com.github.pagehelper.page.PageMethod;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserManager {

	@Resource
	private UserMapper userMapper;

	public UserEntity getUserByAccount(String account) {
		return userMapper.selectByAccount(account);
	}

	public List<UserEntity> getUserList(int pageNum, int pageSize, UserEntity userEntity) {
		PageMethod.startPage(pageNum, pageSize);
		return userMapper.select(userEntity);
	}

	public long getTotal(UserEntity userEntity) {
		return userMapper.selectCount(userEntity);
	}

	public void insertUser(UserEntity userEntity) {
		userMapper.insertSelective(userEntity);
	}

	public void updateByPrimaryKeySelective(UserEntity userEntity) {
		userMapper.updateByPrimaryKeySelective(userEntity);
	}

	public void changePassword(Integer id,String password){
		userMapper.changePassword(id,password);
	}

	public UserInfo searchByEmployeNumber(Integer employeeNumber,Integer companyId){
		return userMapper.searchByEmployeNumber(employeeNumber,companyId);
	}
	public UserInfo searchById(Integer id,Integer companyId){
		return ExchangeUtils.exchangeObject(userMapper.searchById(id,companyId),UserInfo.class);
	}
	public UserInfo getByIdInSameCompany(Integer userId){
		return ExchangeUtils.exchangeObject(userMapper.searchByIdSameCompany(userId), UserInfo.class);
	}

	public UserInfo getById(Integer userId){
		return ExchangeUtils.exchangeObject(userMapper.getById(userId), UserInfo.class);
	}
}
