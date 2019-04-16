package com.ecms.web.filter;

import com.ecms.bean.UserInfo;
import com.ecms.common.Constants;
import com.ecms.common.LogCapability;
import com.ecms.utils.ResultUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFilter implements Filter, LogCapability {

	public static final String USER_SESSION = "userSession";
	private static final ThreadLocal<UserInfo> user = new ThreadLocal<UserInfo>();
	private Logger logger = getLogger();

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public static UserInfo getCurrentUser() {
		return user.get();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uri = request.getRequestURI();
		if (!uri.contains("/login") && !uri.contains("/appLogin") && !uri.contains("/version/android")) {
			try {
				UserInfo userInfo = null;
				String token = request.getHeader(Constants.APP_HEADER_NAME);
				if (StringUtils.isNotBlank(token)) {
					String value = stringRedisTemplate.opsForValue().get(token);
					if (StringUtils.isNotBlank(value)) {
						userInfo = (UserInfo) JSONObject.toBean(JSONObject.fromObject(value), UserInfo.class);
					}
				} else {
					userInfo = (UserInfo) request.getSession().getAttribute(USER_SESSION);
				}
				if (userInfo == null) {
					String originHeader = request.getHeader("Origin");
					HttpServletResponse response = (HttpServletResponse) servletResponse;
					response.reset();
					response.setHeader("Access-Control-Allow-Origin", originHeader);
					response.setHeader("Access-Control-Allow-Credentials", "true");
					response.setContentType("application/json;charset=utf-8");
					response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
					PrintWriter writer = response.getWriter();
					writer.write(getNoLoginMsg());
					writer.flush();
					writer.close();
					return;
				} else {
					user.set(userInfo);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return;
			}
		}
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			user.remove();
		}
	}

	private String getNoLoginMsg() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("code", ResultUtils.CODE_NOLOGIN);
		return jsonObject.toString();
	}

	@Override
	public void destroy() {
	}

}
