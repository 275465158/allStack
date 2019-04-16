package com.ecms.service;

import com.ecms.bean.UserInfo;
import com.ecms.common.LogCapability;
import com.ecms.common.Pager;
import com.ecms.dal.entity.UserEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.UserManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements LogCapability {

	private Logger logger = getLogger();
	private static final int STATUS_DEFAULT = 0;
	private static final int STATUS_INVALID = 10;
	private static final String DEFAULT_PASSWORD = "123456";
	@Resource
	private UserManager userManager;
	@Resource
	private SellerCustomerService sellerCustomerService;

    public UserInfo getLoginUser(String account, String password) throws CommonBusinessException {
        UserEntity userEntity = userManager.getUserByAccount(account);
        if (userEntity == null) {
            throw new CommonBusinessException("用户账号不存在");
        } else if (!userEntity.getPassword().equals(password)) {
            throw new CommonBusinessException("密码错误");
        } else if (userEntity.getStatus() > 0) {
            throw new CommonBusinessException("用户已失效");
        }
        return ExchangeUtils.exchangeObject(userEntity, UserInfo.class);
    }

	public Pager<UserInfo> getUserList(int pageNum, int pageSize, UserInfo userInfo) {
		UserEntity userEntity = ExchangeUtils.exchangeObject(userInfo, UserEntity.class);
		userEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<UserEntity> list = userManager.getUserList(pageNum, pageSize, userEntity);
		long total = userManager.getTotal(userEntity);
		List<UserInfo> userList = ExchangeUtils.exchangeList(list, UserInfo.class);
		Pager<UserInfo> pager = new Pager<UserInfo>(pageNum, pageSize, total, userList);
		return pager;
	}

	public void insertUser(UserInfo userInfo) {
    	if (userInfo.getUserAccount().contains(".")) {
    		throw new CommonBusinessException("账号不能带.字符");
		}
		setRoles(userInfo);
		UserEntity userEntity = ExchangeUtils.exchangeObject(userInfo, UserEntity.class);
		UserInfo currentUser = LoginFilter.getCurrentUser();
		String accountName = currentUser.getUserAccount();
		if (accountName.contains(".")) {
			accountName = accountName.substring(0, accountName.indexOf("."));
		}
		userEntity.setUserAccount(accountName + "." + userInfo.getUserAccount());
		userEntity.setPassword(DEFAULT_PASSWORD);
		userEntity.setCompanyId(currentUser.getCompanyId());
		userEntity.setParentId(currentUser.getId());
		userManager.insertUser(userEntity);
	}

	public void saveRole(UserInfo userInfo) {
		setRoles(userInfo);
		userManager.updateByPrimaryKeySelective(ExchangeUtils.exchangeObject(userInfo, UserEntity.class));
	}

	private void setRoles(UserInfo userInfo) {
		List<Integer> list = userInfo.getCheckList();
		if (!CollectionUtils.isEmpty(userInfo.getCheckList())) {
			int role = list.get(0);
			for (Integer value : userInfo.getCheckList()) {
				role = role | value;
			}
			userInfo.setRole(role);
		}
	}

	public void insertCustomer(UserInfo userInfo) {
    	userInfo.setUserName(sellerCustomerService.getModel(Integer.valueOf(userInfo.getCustomerId())).getCustomerName());
		UserEntity userEntity = ExchangeUtils.exchangeObject(userInfo, UserEntity.class);
		UserInfo currentUser = LoginFilter.getCurrentUser();
		userEntity.setUserAccount(currentUser.getUserAccount() + "." + userInfo.getUserAccount());
		userEntity.setPassword(DEFAULT_PASSWORD);
		userEntity.setCompanyId(currentUser.getCompanyId());
		userEntity.setParentId(currentUser.getId());
		userManager.insertUser(userEntity);
	}

	public void updateStatus(int id, int status) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(id);
		userEntity.setStatus(status == STATUS_DEFAULT ? STATUS_INVALID : STATUS_DEFAULT);
		userManager.updateByPrimaryKeySelective(userEntity);
	}

	public void changePassword(Integer id,String password){
    	userManager.changePassword(id,password);
	}

	public UserInfo searchByEmployeNumber(Integer employeeNumber,Integer companyId){
    	return userManager.searchByEmployeNumber(employeeNumber,companyId);
	}
	public UserInfo searchById(Integer id,Integer companyId){
		return userManager.searchById(id,companyId);
	}

	/**
	 * 根据雇员id查询用户
	 * @param employeeId
	 * @return
	 */
	public UserInfo getByIdInSameCompany(Integer employeeId){
		return userManager.getByIdInSameCompany(employeeId);
	}


	/**
	 * 按照用户id获取实体
	 * @param userId
	 * @return
	 */
	public UserInfo getById(Integer userId){return userManager.getById(userId);}
}
