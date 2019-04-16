package com.ecms.web.controller;

import com.ecms.bean.UserInfo;
import com.ecms.common.Constants;
import com.ecms.common.LogCapability;
import com.ecms.common.Result;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.service.UserService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController implements LogCapability {

	private Logger logger = getLogger();
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public Result<UserInfo> login(HttpServletRequest request, @RequestParam("account") String account,
			@RequestParam("password") String password) {
		Result<UserInfo> result;
		try {
			UserInfo userInfo = userService.getLoginUser(account, password);
			request.getSession().setAttribute(LoginFilter.USER_SESSION, userInfo);
			result = ResultUtils.success(userInfo);
		} catch (CommonBusinessException e) {
			result = ResultUtils.failure(e);
		}
		logger.info("用户[{}]尝试登陆系统, 结果:{}", account, result.getMessage());
		return result;
	}

	@RequestMapping("/appLogin")
	public Result<UserInfo> appLogin(@RequestParam("account") String account, @RequestParam("password") String password) {
		Result<UserInfo> result;
		try {
			UserInfo userInfo = userService.getLoginUser(account, password);
			String token = UUID.randomUUID().toString();
			stringRedisTemplate.opsForValue().set(token, JSONObject.fromObject(userInfo).toString(), 1, TimeUnit.DAYS);
			userInfo.setToken(token);
			result = ResultUtils.success(userInfo);
		} catch (CommonBusinessException e) {
			result = ResultUtils.failure(e);
		}
		logger.info("用户[{}]从app尝试登陆系统, 结果:{}", account, result.getMessage());
		return result;
	}

	@RequestMapping("/appLogout")
	public Result<Void> appLogout(HttpServletRequest request) {
		Result<Void> result;
		try {
			String token = request.getHeader(Constants.APP_HEADER_NAME);
			stringRedisTemplate.delete(token);
			result = ResultUtils.success();
		} catch (CommonBusinessException e) {
			result = ResultUtils.failure(e);
		}
		logger.info("用户[{}]退出登录");
		return result;
	}

	@RequestMapping("/dashboard")
	public Result<Void> dashboard() {
		return ResultUtils.success();
	}

}
