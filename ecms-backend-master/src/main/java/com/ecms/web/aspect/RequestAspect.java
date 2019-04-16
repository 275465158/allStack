package com.ecms.web.aspect;

import com.ecms.common.LogCapability;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.utils.ResultUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RequestAspect implements LogCapability {

	private Logger logger = getLogger();

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object process(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (Exception e) {
			try {
				Signature sig = pjp.getSignature();
				Method method = ((MethodSignature) sig).getMethod();
				String className = method.getDeclaringClass().getName();
				logger.error("Controller:[{}]-方法[{}]出现系统错误, 参数{}, ", className, method.getName(), pjp.getArgs(), e);
				logger.error(e.getMessage(), e);
				return ResultUtils.failure(e instanceof CommonBusinessException ? e.getMessage() : "系统错误");
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
				return ResultUtils.failure("未知错误");
			}
		}
	}
}
