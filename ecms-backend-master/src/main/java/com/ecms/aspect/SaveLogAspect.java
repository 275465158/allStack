package com.ecms.aspect;

import com.alibaba.fastjson.JSON;
import com.ecms.annotation.SaveLog;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class SaveLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SaveLogAspect.class);

    @Around("@annotation(com.ecms.annotation.SaveLog)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        doLog(pjp);
        return pjp.proceed();
    }

    private void doLog(ProceedingJoinPoint pjp) {
        try {
            MethodSignature signature = ((MethodSignature) pjp.getSignature());
            Method method = signature.getMethod();
            SaveLog saveLog = method.getAnnotation(SaveLog.class);
            String name = saveLog.name();
            if (StringUtils.isBlank(name)) {
                Class clazz = signature.getDeclaringType();
                name = clazz.getName() + "-" + method.getName();
            }
            Object[] args = pjp.getArgs();
            String arg = "";
            if (args != null && args.length > 0) {
                arg = JSON.toJSONString(args);
            }
            logger.info("操作人:{}, 方法:{}, 参数:{}", LoginFilter.getCurrentUser().getId(), name, arg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
